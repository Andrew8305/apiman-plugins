/*
 * Copyright 2016 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.plugins.jwt;

import io.apiman.gateway.engine.beans.ApiRequest;
import io.apiman.gateway.engine.policies.AbstractMappedPolicy;
import io.apiman.gateway.engine.policy.IPolicyChain;
import io.apiman.gateway.engine.policy.IPolicyContext;
import io.apiman.plugins.jwt.beans.JWTPolicyBean;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;

import java.util.Optional;

/**
 * Generic JWT/S Policy.
 *
 * @author Marc Savy {@literal <msavy@redhat.com>}
 */
public class JWTPolicy extends AbstractMappedPolicy<JWTPolicyBean> {

    private static final String AUTHORIZATION_KEY = "Authorization"; //$NON-NLS-1$
    private static final String ACCESS_TOKEN_QUERY_KEY = "access_token"; //$NON-NLS-1$
    private static final String BEARER = "bearer "; //$NON-NLS-1$
    private static final PolicyFailureFactory FAILURE_FACTORY = PolicyFailureFactory.getInstance();

    @Override
    protected Class<JWTPolicyBean> getConfigurationClass() {
        return JWTPolicyBean.class;
    }

    @Override
    protected void doApply(ApiRequest request, IPolicyContext context, JWTPolicyBean config, IPolicyChain<ApiRequest> chain) {
        String jwt = Optional.ofNullable(request.getHeaders().get(AUTHORIZATION_KEY))
                // If seems to be bearer token
                .filter(e -> e.toLowerCase().startsWith(BEARER))
                // Get out token value
                .map(e -> e.substring(BEARER.length(), e.length()))
                // Otherwise attempt to get from the access_token query param
                .orElse(request.getQueryParams().get(ACCESS_TOKEN_QUERY_KEY));

        // If transport security required and is not secure.
        if (config.getRequireTransportSecurity() && !request.isTransportSecure()) {
            chain.doFailure(FAILURE_FACTORY.noTransportSecurity(context));
            return;
        }

        // If JWT required and none provided
        if (config.getRequireJWT() && jwt == null) {
            chain.doFailure(FAILURE_FACTORY.noAuthenticationProvided(context));
            return;
        }

        if (jwt != null) {
            try {
                validateJwt(jwt, request, config);
                stripAuthTokens(request, config);
                chain.doApply(request);
            } catch (ExpiredJwtException e) {
                chain.doFailure(FAILURE_FACTORY.jwtExpired(context, e));
            } catch (PrematureJwtException e) {
                chain.doFailure(FAILURE_FACTORY.jwtPremature(context, e));
            } catch (MalformedJwtException e) {
                chain.doFailure(FAILURE_FACTORY.jwtMalformed(context, e));
            } catch (SignatureException e) {
                chain.doFailure(FAILURE_FACTORY.signatureException(context, e));
            } catch (InvalidClaimException e) {
                chain.doFailure(FAILURE_FACTORY.invalidClaim(context, e));
            } catch (Exception e) {
                chain.doFailure(FAILURE_FACTORY.genericFailure(context, e));
            }
        } else {
            chain.doApply(request);
        }
    }

    private void validateJwt(String token, ApiRequest request, JWTPolicyBean config) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        JwtParser parser = Jwts.parser()
                .setSigningKey(config.getSigningKey())
                .setAllowedClockSkewSeconds(config.getAllowedClockSkew());

        // Set all claims
        config.getRequiredClaims().stream() // TODO add type variable to allow dates, etc
            .forEach(requiredClaim -> parser.require(requiredClaim.getClaimName(), requiredClaim.getClaimValue()));

        parser.parse(token); // TODO need to forward fields where claims are available.
    }

    private void stripAuthTokens(ApiRequest request, JWTPolicyBean config) {
        if (config.getStripTokens()) {
            request.getHeaders().remove(AUTHORIZATION_KEY);
            request.getQueryParams().remove(ACCESS_TOKEN_QUERY_KEY);
        }
    }
}

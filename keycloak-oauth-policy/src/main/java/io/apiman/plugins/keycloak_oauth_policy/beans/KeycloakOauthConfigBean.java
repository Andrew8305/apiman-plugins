/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS I/me S" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.plugins.keycloak_oauth_policy.beans;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * KeyCloak OAuth Policy Configuration
 * 
 * @author Marc Savy <msavy@redhat.com>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "requireOauth", "blacklistUnsafeTokens", "stripTokens", "realm",
        "realmCertificateString", "forwardAuthInfo" })
public class KeycloakOauthConfigBean {

    /**
     * Require OAuth
     * <p>
     * Terminate request if no OAuth is provided.
     */
    @JsonProperty("requireOauth")
    private Boolean requireOauth = true;
    /**
     * Blacklist unsafe tokens
     * <p>
     * Any tokens used without transport security will be blackedlisted to mitigate associated security risks.
     */
    @JsonProperty("blacklistUnsafeTokens")
    private Boolean blacklistUnsafeTokens = true;
    /**
     * Strip tokens
     * <p>
     * Remove any Authorization header or token query parameter before forwarding traffic to the Service.
     * 
     */
    @JsonProperty("stripTokens")
    private Boolean stripTokens;
    /**
     * Realm
     * <p>
     * Realm name
     */
    @JsonProperty("realm")
    private String realm;
    /**
     * Keycloak realm certificate
     * <p>
     * To validate OAuth requests. Must be a PEM-encoded X.509 certificate, including bounding strings.
     */
    @JsonProperty("realmCertificateString")
    private String realmCertificateString;
    /**
     * Forward Keycloak token information
     * <p>
     * Fields from the token can be set as headers and forwarded to the Service. Access_token corresponds to
     * the full token.
     */
    @JsonProperty("forwardAuthInfo")
    private List<ForwardAuthInfo> forwardAuthInfo = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    private Certificate realmCertificate;

    /**
     * Require OAuth
     * <p>
     * Terminate request if no OAuth is provided.
     * 
     * @return The requireOauth
     */
    @JsonProperty("requireOauth")
    public Boolean getRequireOauth() {
        return requireOauth;
    }

    /**
     * Require OAuth
     * <p>
     * Terminate request if no OAuth is provided.
     * 
     * @param requireOauth The requireOauth
     */
    @JsonProperty("requireOauth")
    public void setRequireOauth(Boolean requireOauth) {
        this.requireOauth = requireOauth;
    }

    /**
     * Blacklist unsafe tokens
     * <p>
     * Any tokens used without transport security will be blackedlisted to mitigate associated security risks.
     * 
     * @return The blacklistUnsafeTokens
     */
    @JsonProperty("blacklistUnsafeTokens")
    public Boolean getBlacklistUnsafeTokens() {
        return blacklistUnsafeTokens;
    }

    /**
     * Blacklist unsafe tokens
     * <p>
     * Any tokens used without transport security will be blackedlisted to mitigate associated security risks.
     * 
     * @param blacklistUnsafeTokens The blacklistUnsafeTokens
     */
    @JsonProperty("blacklistUnsafeTokens")
    public void setBlacklistUnsafeTokens(Boolean blacklistUnsafeTokens) {
        this.blacklistUnsafeTokens = blacklistUnsafeTokens;
    }

    /**
     * Strip tokens
     * <p>
     * Remove any Authorization header or token query parameter before forwarding traffic to the Service.
     * 
     * @return The stripTokens
     */
    @JsonProperty("stripTokens")
    public Boolean getStripTokens() {
        return stripTokens;
    }

    /**
     * Strip tokens
     * <p>
     * Remove any Authorization header or token query parameter before forwarding traffic to the Service.
     * 
     * @param stripTokens The stripTokens
     */
    @JsonProperty("stripTokens")
    public void setStripTokens(Boolean stripTokens) {
        this.stripTokens = stripTokens;
    }

    /**
     * Realm
     * <p>
     * Realm name
     * 
     * @return The realm
     */
    @JsonProperty("realm")
    public String getRealm() {
        return realm;
    }

    /**
     * Realm
     * <p>
     * Realm name
     * 
     * @param realm The realm
     */
    @JsonProperty("realm")
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * Keycloak realm certificate
     * <p>
     * To validate OAuth requests. Must be a PEM-encoded X.509 certificate, including bounding strings.
     * 
     * @return The realmCertificateString
     */
    @JsonProperty("realmCertificateString")
    public String getRealmCertificateString() {
        return realmCertificateString;
    }

    /**
     * Keycloak realm certificate
     * <p>
     * To validate OAuth requests. Must be a PEM-encoded X.509 certificate, including bounding strings.
     * 
     * @param realmCertificateString The realmCertificateString
     */
    @JsonProperty("realmCertificateString")
    public void setRealmCertificateString(String realmCertificateString) {
        this.realmCertificateString = realmCertificateString;
        realmCertificate = generateCertificate(realmCertificateString);
    }

    /**
     * Forward Keycloak token information
     * <p>
     * Fields from the token can be set as headers and forwarded to the Service. Access_token corresponds to
     * the full token.
     * 
     * @return The forwardAuthInfo
     */
    @JsonProperty("forwardAuthInfo")
    public List<ForwardAuthInfo> getForwardAuthInfo() {
        return forwardAuthInfo;
    }

    /**
     * Forward Keycloak token information
     * <p>
     * Fields from the token can be set as headers and forwarded to the Service. Access_token corresponds to
     * the full token.
     * 
     * @param forwardAuthInfo The forwardAuthInfo
     */
    @JsonProperty("forwardAuthInfo")
    public void setForwardAuthInfo(List<ForwardAuthInfo> forwardAuthInfo) {
        this.forwardAuthInfo = forwardAuthInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Certificate getRealmCertificate() {
        return realmCertificate;
    }

    @SuppressWarnings("nls")
    private Certificate generateCertificate(String data) {
        try {
            InputStream is = IOUtils.toInputStream(data, "UTF-8");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            @SuppressWarnings("unchecked")
            Collection<X509Certificate> certificates = (Collection<X509Certificate>) cf
                    .generateCertificates(is);

            if (certificates.size() == 0) {
                throw new RuntimeException("Certificate contains no key.");
            }

            // TODO find more relevant exceptions
            if (certificates.size() > 1) {
                throw new RuntimeException(
                        "More than one certificate provided in X.509, please use only one.");
            }

            return certificates.iterator().next();

        } catch (CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

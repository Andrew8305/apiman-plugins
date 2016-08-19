/*
 * Copyright 2014 JBoss Inc
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
package io.apiman.plugins.url_policy;

/**
 * Configuration object for the URL re-writing policy.
 */
public class URLRewritingConfig {

    private String fromRegex;
    private String toReplacement;
    private boolean processBody;
    private boolean processHeaders;

    /**
     * Constructor.
     */
    public URLRewritingConfig() {
    }

    /**
     * @return the fromRegex
     */
    public String getFromRegex() {
        return fromRegex;
    }

    /**
     * @param fromRegex the fromRegex to set
     */
    public void setFromRegex(String fromRegex) {
        this.fromRegex = fromRegex;
    }

    /**
     * @return the toReplacement
     */
    public String getToReplacement() {
        return toReplacement;
    }

    /**
     * @param toReplacement the toReplacement to set
     */
    public void setToReplacement(String toReplacement) {
        this.toReplacement = toReplacement;
    }

    /**
     * @return the processBody
     */
    public boolean isProcessBody() {
        return processBody;
    }

    /**
     * @param processBody the processBody to set
     */
    public void setProcessBody(boolean processBody) {
        this.processBody = processBody;
    }

    /**
     * @return the processHeaders
     */
    public boolean isProcessHeaders() {
        return processHeaders;
    }

    /**
     * @param processHeaders the processHeaders to set
     */
    public void setProcessHeaders(boolean processHeaders) {
        this.processHeaders = processHeaders;
    }

}

/*
 * Copyright 2016 Adobe Systems Incorporated. All rights reserved.
 *
 * This file is licensed to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS OF ANY KIND,
 * either express or implied.  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adobe.api.platform.msc.support;

import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: ccristia
 * Date: 1/24/14
 * Time: 2:44 PM
 */
@Produces(MediaType.TEXT_PLAIN)
@Path("/health-check")
@JaxRsComponent
public class HealthCheckResource {

    @Value("${health-check.message:}")
    public String HEALTH_CHECK_MESSAGE;

    @Value("${application.name:unknown}")
    private String applicationName;

    @Value("${build.version:unknown}")
    private String buildVersion;

    @Value("${build.timestamp:unknown}")
    private String buildTimestamp;

    @Value("${build.number:unknown}")
    private String buildNumber;

    @GET
    public String getHealthCheckMessage() {
        return HEALTH_CHECK_MESSAGE;
    }

    @GET
    @Path("/version")
    public String getAppVersion() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Application info]");
        sb.append("\nApplication name : " + applicationName);
        sb.append("\nBuild version    : " + buildVersion);
        sb.append("\nBuild timestamp  : " + buildTimestamp);
        sb.append("\nBuild number     : " + buildNumber);
        return sb.toString();
    }
}

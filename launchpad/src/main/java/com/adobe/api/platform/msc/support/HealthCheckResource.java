/*******************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2014 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 ******************************************************************************/

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

    @GET
    public String getHealthCheckMessage() {
        return HEALTH_CHECK_MESSAGE;
    }

}

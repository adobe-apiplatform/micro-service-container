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

package com.adobe.api.platform.msc.test.support;

import com.adobe.api.platform.msc.support.JaxRsComponent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cristian Constantin
 * @since 11/20/14
 */
@Path("/test")
@JaxRsComponent
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

    @GET
    public Map<String, Object> get() {

        Map<String, Object> map = new HashMap<>();
        map.put("valid", true);
        return map;
    }

    @GET
    @Path("/error")
    public String endpointError() {

        throw new RuntimeException();
    }
}

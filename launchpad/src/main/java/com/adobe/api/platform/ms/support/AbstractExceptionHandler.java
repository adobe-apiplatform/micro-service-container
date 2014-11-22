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

package com.adobe.api.platform.ms.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class that provides basic exception handling to subclasses.
 * <p/>
 * User: ccristia
 * Date: 5/30/13
 */
@Produces(MediaType.APPLICATION_JSON)
@Provider
public abstract class AbstractExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Response toResponse(String message, Response.Status status) {

        Map<String, String> retVal = new HashMap<>();

        retVal.put("error", message);
        retVal.put("uid", MDC.get("requestId"));

        return Response.status(status).entity(retVal).build();
    }
}

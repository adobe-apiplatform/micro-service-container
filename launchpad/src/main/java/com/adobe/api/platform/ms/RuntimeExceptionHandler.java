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

package com.adobe.api.platform.ms;

import com.adobe.api.platform.ms.support.AbstractExceptionHandler;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Intercepts uncaught application errors, logs them and provides some
 * brief information to the client.
 * <p/>
 * User: ccristia
 * Date: 5/30/13
 */
public class RuntimeExceptionHandler extends AbstractExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        else {

            logger.error("Unexpected error", exception);

            return toResponse("Unexpected server error. Please try again later.",
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

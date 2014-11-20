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
 * suppliers and are protected by all applicable intellectual property laws,
 * including trade secret and or copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 ******************************************************************************/

package com.adobe.api.platform.ms.test.client;

import javax.ws.rs.core.Response;

/**
 * User: ccristia
 * Date: 2/26/14
 */
public class RestCallError extends RuntimeException {

    private Response clientResponse;

    public RestCallError(String message, Response clientResponse) {
        super(message);
        this.clientResponse = clientResponse;
    }

    public String getResponseAsString() {
        return clientResponse.readEntity(String.class);
    }

    public Response getClientResponse() {
        return clientResponse;
    }
}

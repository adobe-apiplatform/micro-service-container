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

package com.adobe.api.platform.ms.test;

import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author Cristian Constantin
 * @since 11/20/14
 */
public class ContainerIntegrationTest extends BaseTest {

    @Test
    public void containerRest() {

        Map response = getRestClient()
                .path("test")
                .get(Map.class);

        assertTrue((Boolean) response.get("valid"));
    }


    @Test
    public void testErrorHandling() {

        Response response = getRestClient()
                .path("test")
                .path("v3")
                .get(Response.class);

        System.out.println(response.readEntity(String.class));
    }
}

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

package com.adobe.api.platform.msc.test;

import com.adobe.api.platform.msc.test.support.TestBean;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
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
                .queryParam("name", "hello")
                .get(Map.class);

        assertTrue((Boolean) response.get("valid"));
        assertEquals("hello", response.get("name"));

        String result = getRestClient()
                .path("test")
                .post(String.class, new TestBean("hello"));
        assertEquals("hello", result);


        List<String> list = getRestClient()
                .path("test").path("all")
                .getList(String.class);
        assertEquals(2, list.size());
        assertEquals("two", list.get(1));
    }

    @Test
    public void testErrorHandling() {

        Response response = getRestClient()
                .path("test")
                .path("error")
                .get(Response.class);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        Map entity = response.readEntity(Map.class);
        assertTrue(entity.containsKey("uid"));
        assertTrue(entity.containsKey("error"));


        response = getRestClient()
                .path("test")
                .path("error")
                .header("X-Request-Id", "1234")
                .get(Response.class);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        entity = response.readEntity(Map.class);
        assertTrue(entity.containsKey("uid"));
        assertEquals("1234", entity.get("uid"));
    }
}

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

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Object> get(@QueryParam("name") String name) {

        Map<String, Object> map = new HashMap<>();
        map.put("valid", true);
        map.put("name", name);
        return map;
    }

    @GET
    @Path("all")
    public List<String> get() {

        return Arrays.asList("one", "two");
    }

    @GET
    @Path("testDelay")
    public List<String> getWithDelay(@QueryParam("delay") int delay) {
        //sleep for the specified period of time
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList("one", "two");
    }

    @GET
    @Path("/error")
    public String endpointError() {

        throw new RuntimeException();
    }

    @GET
    @Path("/hateoas")
    public TestBean hateoas() {

        TestBean bean = new TestBean();

        bean.setLinks(Arrays.asList(Link.fromResource(this.getClass())
                .rel("test")
                .title("Test")
                .build()));

        return bean;
    }

    @POST
    public String post(TestBean testBean) {

        return testBean.getName();
    }

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public TestBean post(String name) {
        return new TestBean(name);
    }

    @GET
    @Path("/query-param-validation")
    public void testNullParam(@NotNull @QueryParam("param") String param) {

    }
}

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

package com.adobe.api.platform.msc.test.support;

import com.adobe.api.platform.msc.support.JaxRsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GET
    public Map<String, Object> get(@QueryParam("name") String name) {

        logger.debug("Received request.");

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

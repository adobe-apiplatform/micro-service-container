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

package com.adobe.api.platform.msc.test;

import com.adobe.api.platform.msc.test.support.TestBean;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * @author Cristian Constantin
 * @since 11/20/14
 */
public class ContainerIntegrationTest extends BaseTest {

    @Test
    public void containerRest() {

        Map response = getRestClient()
                .path("test")
                .header("X-Request-Id", "123456")
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
    public void testAsyncRequest() {

        //test that second api request is processed before first request
        boolean[] flags = new boolean[1];

        getRestClient()
                .path("test").path("testDelay")
                .queryParam("delay", "3000")
                .getAsync(new InvocationCallback<List<String>>() {
                    @Override
                    public void completed(List<String> list) {
                        assertEquals(2, list.size());
                        assertEquals("two", list.get(1));
                        assertTrue(flags[0]);
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        throw new RuntimeException("This shouldn't happen");
                    }
                });

        getRestClient()
                .path("test").path("testDelay")
                .queryParam("delay", "1000")
                .getAsync(new InvocationCallback<List<String>>() {
                    @Override
                    public void completed(List<String> list) {
                        assertEquals(2, list.size());
                        assertEquals("two", list.get(1));
                        flags[0] = true;
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        throw new RuntimeException("This shouldn't happen");
                    }
                });

        //Need to sleep current thread. Otherwise it will exit and test context will be destroyed.
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostAsyncRequest() throws ExecutionException, InterruptedException {

        Future<String> result = getRestClient()
                .path("test")
                .postAsync(new TestBean("hello"), String.class);
        assertEquals("hello", result.get());

        getRestClient()
                .path("test")
                .postAsync(new TestBean("hello"));

        final String[] callbackResult = new String[1];

        result = getRestClient()
                .path("test")
                .postAsync(new TestBean("hello"), new InvocationCallback<String>() {
                    @Override
                    public void completed(String s) {
                        callbackResult[0] = s;
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });

        assertEquals("hello", result.get());
        assertEquals("hello", callbackResult[0]);
    }


    @Test
    public void testHealthCheck() {

        String response = getRestClient()
                .path("health-check")
                .acceptedMediaTypes(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);

        assertEquals("MSC is running.", response);
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

    @Test
    public void testHateoas() {

        TestBean response = getRestClient()
                .path("test")
                .path("hateoas")
                .get(TestBean.class);

        assertNotNull(response.getLinks());
        assertEquals(1, response.getLinks().size());
        assertEquals("test", response.getLinks().get(0).getRel());
        assertEquals("Test", response.getLinks().get(0).getTitle());
    }

    @Test
    public void testPostData() {

        String response = getRestClient()
                .path("test")
                .post(String.class, new TestBean("test-name"));

        assertEquals("test-name", response);
    }

    @Test
    public void testPutData() {

        TestBean response = getRestClient()
                .path("test")
                .contentType(MediaType.TEXT_PLAIN_TYPE)
                .put(TestBean.class, "test-put");

        assertEquals("test-put", response.getName());
    }

    @Test
    public void testNullQueryParameter() {
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), getRestClient().path("test").path("query-param-validation").get(Response.class).getStatus());
    }
}

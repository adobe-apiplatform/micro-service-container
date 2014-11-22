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

package com.adobe.api.platform.ms.test;

import com.adobe.api.platform.ms.SpringBootApplication;
import com.adobe.api.platform.ms.client.RestClient;
import com.adobe.api.platform.ms.client.RestClientFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Base test class to be extended by all API Platform Integration Tests
 * <p/>
 * User: ccristia
 * Date: 12/12/13
 */
public class BaseTest {

    protected static ConfigurableApplicationContext context;

    @BeforeClass
    public static void initSpringBootContainer() throws Exception {
        Future<ConfigurableApplicationContext> future = Executors
                .newSingleThreadExecutor().submit(
                        new Callable<ConfigurableApplicationContext>() {
                            @Override
                            public ConfigurableApplicationContext call() throws Exception {
                                return SpringApplication
                                        .run(SpringBootApplication.class);
                            }
                        });
        context = future.get(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }

    protected static final String BASE_URI = "http://localhost:50000/api";

    protected static RestClient getRestClient() {
        return RestClientFactory.getInstance(BASE_URI);
    }
}

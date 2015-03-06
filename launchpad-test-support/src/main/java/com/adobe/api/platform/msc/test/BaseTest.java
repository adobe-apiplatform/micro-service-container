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

package com.adobe.api.platform.msc.test;

import com.adobe.api.platform.msc.SpringBootApplication;
import com.adobe.api.platform.msc.client.RestClient;
import com.adobe.api.platform.msc.client.RestClientFactory;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base test class to be extended by all API Platform Integration Tests
 * <p>
 * User: ccristia
 * Date: 12/12/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApplication.class)
@IntegrationTest("server.port:50000")
@Ignore
public class BaseTest {

    protected static final String BASE_URI = "http://localhost:50000/api";

    protected static RestClient getRestClient() {
        return RestClientFactory.getInstance(BASE_URI);
    }


}

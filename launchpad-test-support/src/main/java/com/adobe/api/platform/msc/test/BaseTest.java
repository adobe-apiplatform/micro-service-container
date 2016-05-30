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

import com.adobe.api.platform.msc.SpringBootApplication;
import com.adobe.api.platform.msc.client.RestClient;
import com.adobe.api.platform.msc.client.RestClientFactory;
import com.adobe.api.platform.msc.client.service.RestClientService;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base test class to be extended by all API Platform Integration Tests
 *
 * User: ccristia
 * Date: 12/12/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApplication.class)
@IntegrationTest("server.port:50000")
@Ignore
public class BaseTest {

    protected static final String BASE_URI = "http://localhost:50000/api";

    @Autowired
    private RestClientService restClientService;

    protected RestClient getRestClient() {
        return restClientService.getRestClient(BASE_URI);
//        return RestClientFactory.getInstance(BASE_URI);
    }
}

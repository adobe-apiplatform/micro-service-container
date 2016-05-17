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

package com.adobe.api.platform.msc.client.service;

import com.adobe.api.platform.msc.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * User: maftei
 * Date: 17/03/15
 */
@Component
public class RestClientService {

    @Autowired
    private Client restClient;

    public RestClient getRestClient(UriBuilder endpoint) {
        return new RestClient(restClient.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }

    public RestClient getRestClient(String endpoint) {
        return new RestClient(restClient.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }
}
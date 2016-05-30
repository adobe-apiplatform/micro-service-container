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

package com.adobe.api.platform.msc.client;

import com.adobe.api.platform.msc.client.jackson.JacksonConfig;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.security.cert.X509Certificate;

/**
 * Factory class that initializes JAX-RS Client objects.
 * Adds Jackson JSON support and takes care of the SSL configuration.
 *
 * User: ccristia
 * Date: 8/20/13
 * Time: 10:52 AM
 *
 * This is deprecated. You should use {@code RestClientService} instead.
 */
@Deprecated
public class RestClientFactory {

    public static RestClient getInstance(UriBuilder endpoint) {
        Client client = getInstance();
        return new RestClient(client.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }

    public static RestClient getInstance(String endpoint) {
        Client client = getInstance();
        return new RestClient(client.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }

    private static Client getInstance() {

        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }};

            // Ignore differences between given hostname and certificate hostname
            HostnameVerifier hv = (hostname, session) -> true;

            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);


            return ClientBuilder.newBuilder()
                    .sslContext(ctx)
                    .hostnameVerifier(hv)
                    .register(JacksonConfig.class)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

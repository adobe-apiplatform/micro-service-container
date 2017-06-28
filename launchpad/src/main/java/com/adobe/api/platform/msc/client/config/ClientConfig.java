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

package com.adobe.api.platform.msc.client.config;

import com.adobe.api.platform.msc.client.jackson.JacksonConfig;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: maftei
 * Date: 17/03/15
 */
@Configuration
public class ClientConfig {

    /**
     * HTTP persistent connection pool size (used by resteasy web client).
     */
    @Value("${http.connection_pool.size:}")
    private Integer connectionPoolSize;

    @Value("${http.connection.ttl:}")
    private Integer connectionTTL;

    @Value("${http.connection_timeout:}")
    private Integer connectionTimeout;

    /**
     * Socket timeout parameter for requests
     */
    @Value("${http.socket_timeout:}")
    private Integer socketTimeout;

    /**
     * Time unit that you want to use
     * SECONDS, MILLISECONDS, DAYS, MINUTES
     */
    @Value("${time_unit:SECONDS}")
    private String timeUnit;

    /**
     * Thread pool size (for async operations).
     */
    @Value("${worker.thread_pool.size:}")
    private Integer workerThreadPooSize;

    @Value("${worker.thread_checkout_time:}")
    private Integer checkoutTime;


    /**
     * Used for creating http connections to 3rd party API. It's a heavy-weight object and it's useful to reuse it.
     *
     * @return A JaxRS Client instance
     */
    @Bean
    public Client getJaxrsClient() {
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
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);

            ResteasyClientBuilder builder = new ResteasyClientBuilder()
                    .sslContext(ctx)
                    .register(JacksonConfig.class)
                    .hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.ANY);

            //if not set, builder sets a pool with 10 threads
            if (workerThreadPooSize != null) {
                // if necessary expose the thread pool as bean (reuse the same thread pool for other scenarios).
                builder.asyncExecutor(Executors.newFixedThreadPool(workerThreadPooSize));
            }

            if (connectionPoolSize != null) {
                builder.connectionPoolSize(connectionPoolSize);
            }

            if (connectionTTL != null) {
                builder.connectionTTL(connectionTTL, TimeUnit.valueOf(timeUnit));
            }

            if(connectionTimeout != null) {
                builder.establishConnectionTimeout(connectionTimeout, TimeUnit.valueOf(timeUnit));
            }

            if (socketTimeout != null) {
                builder.socketTimeout(socketTimeout, TimeUnit.valueOf(timeUnit));
            }

            if (checkoutTime != null) {
                builder.connectionCheckoutTimeout(checkoutTime, TimeUnit.valueOf(timeUnit));
            }

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

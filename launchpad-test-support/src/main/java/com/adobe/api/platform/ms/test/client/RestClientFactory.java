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

package com.adobe.api.platform.ms.test.client;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.security.cert.X509Certificate;

/**
 * Factory class that initializes Jersey Client objects.
 * Adds Jackson JSON support and takes care of the SSL configuration.
 * <p/>
 * User: ccristia
 * Date: 8/20/13
 * Time: 10:52 AM
 */
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
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);


            return ClientBuilder.newBuilder()
                    .sslContext(ctx)
                    .hostnameVerifier(hv)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

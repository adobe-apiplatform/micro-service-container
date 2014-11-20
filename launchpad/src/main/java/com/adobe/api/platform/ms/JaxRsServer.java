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

package com.adobe.api.platform.ms;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.core.Application;
import java.net.URISyntaxException;

/**
 * JAX-RS server implementation based on the Undertow and Resteasy.
 * <p/>
 * User: ccristia
 * Date: 2/13/14
 */
@Component
public class JaxRsServer {

    @Value("${server.port:8080}")
    private int port;

    private UndertowJaxrsServer undertowJaxrsServer = null;

    @Autowired
    private Application application;

    @PostConstruct
    public void start() throws URISyntaxException {

        undertowJaxrsServer = new UndertowJaxrsServer().start(Undertow.builder()
                .addHttpListener(port, "0.0.0.0"));

        undertowJaxrsServer.deploy(application);
    }

    @PreDestroy
    public void stop() {
        undertowJaxrsServer.stop();
    }
}
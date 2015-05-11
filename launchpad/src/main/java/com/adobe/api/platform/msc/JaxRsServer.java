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

package com.adobe.api.platform.msc;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.core.Application;
import java.net.URISyntaxException;

/**
 * JAX-RS server implementation based on Undertow and RESTEasy.
 * <p/>
 * User: ccristia
 * Date: 2/13/14
 */
@Component
public class JaxRsServer {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port:8080}")
    private int port;

    @Value("${server.threads.io:0}")
    private int noIoThreads;

    @Value("${server.threads.workers:0}")
    private int noWorkerThreads;

    private UndertowJaxrsServer undertowJaxrsServer = null;

    @Autowired
    private Application application;

    @PostConstruct
    public void start() throws URISyntaxException {

        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(port, "0.0.0.0");

        if (noIoThreads != 0) {
            logger.debug("Undertow custom IO threads: {}", noIoThreads);
            builder.setIoThreads(noIoThreads);
        }
        if (noWorkerThreads != 0) {
            logger.debug("Undertow custom worker threads: {}", noWorkerThreads);
            builder.setWorkerThreads(noWorkerThreads);
        }

        undertowJaxrsServer = new UndertowJaxrsServer().start(builder);
        undertowJaxrsServer.deploy(application);

        logger.info("Available processors: {}", Runtime.getRuntime().availableProcessors());
        logger.info("Starting Undertow on port {}.", port);
    }

    @PreDestroy
    public void stop() {
        undertowJaxrsServer.stop();
    }
}
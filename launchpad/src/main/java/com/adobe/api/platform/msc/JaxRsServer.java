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
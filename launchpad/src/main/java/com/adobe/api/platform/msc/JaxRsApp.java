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

import com.adobe.api.platform.msc.client.jackson.JacksonConfig;
import com.adobe.api.platform.msc.support.RuntimeExceptionHandler;
import com.adobe.api.platform.msc.support.SwaggerApiListingResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cristian Constantin
 * @since 11/20/14
 */
@Component
@ApplicationPath("/api")
public class JaxRsApp extends Application {

    @Autowired(required = false)
    @Qualifier("jax-rs")
    private Object[] beans;

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                RuntimeExceptionHandler.class,
                JacksonConfig.class,
                RequestIdFilter.class,
                SwaggerApiListingResource.class
        ));
    }

    @Override
    public Set<Object> getSingletons() {

        return new HashSet<>(Arrays.asList(beans));
    }
}

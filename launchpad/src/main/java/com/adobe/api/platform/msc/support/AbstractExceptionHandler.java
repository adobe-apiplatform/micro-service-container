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

package com.adobe.api.platform.msc.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class that provides basic exception handling to subclasses.
 * <p/>
 * User: ccristia
 * Date: 5/30/13
 */
@Produces(MediaType.APPLICATION_JSON)
@Provider
public abstract class AbstractExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Response toResponse(String message, Response.Status status) {

        Map<String, String> retVal = new HashMap<>();

        retVal.put("error", message);
        retVal.put("uid", MDC.get("requestId"));

        return Response.status(status).entity(retVal).build();
    }
}

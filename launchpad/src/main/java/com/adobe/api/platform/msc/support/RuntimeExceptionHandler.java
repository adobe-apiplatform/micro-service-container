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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Intercepts uncaught application errors, logs them and provides some
 * brief information to the client.
 * <p/>
 * User: ccristia
 * Date: 5/30/13
 */
public class RuntimeExceptionHandler extends AbstractExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        } else {

            logger.error("Unexpected error", exception);

            return toResponse("Unexpected server error. Please try again later.",
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

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

import com.adobe.api.platform.ms.test.client.util.ParameterizedListType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper of Jersey Rest client which adds logging and generic error handling.
 * <p/>
 * This class is not thread safe and it is not meant to be reused across requests.
 * <p/>
 * User: ccristia
 * Date: 7/5/13
 */
public class RestClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebTarget webResource;
    private MediaType[] acceptedMediaTypes = new MediaType[0];
    private MediaType contentType = MediaType.APPLICATION_JSON_TYPE;
    private Map<String, Object> headers = new HashMap<String, Object>();

    /**
     * Constructs a new REST client instance on top of a Jersey {@link javax.ws.rs.client.WebTarget} object.
     *
     * @param webResource the wrapped Jersey {@link javax.ws.rs.client.WebTarget} object
     */
    public RestClient(WebTarget webResource) {
        this.webResource = webResource;
    }

    /**
     * @see javax.ws.rs.client.WebTarget#path(String)
     */
    public RestClient path(String path) {
        webResource = webResource.path(path);

        return this;
    }

    /**
     * @see javax.ws.rs.client.WebTarget#path(String)
     */
    public RestClient path(String path, String template, Object value) {
        webResource = webResource.path(path).resolveTemplate(template, value);

        return this;
    }

    /**
     * @see javax.ws.rs.client.WebTarget#queryParam(String, Object...)
     */
    public RestClient queryParam(String key, String value) {
        if (key != null && value != null) {
            webResource = webResource.queryParam(key, value);
        }

        return this;
    }

    /**
     * @see javax.ws.rs.client.Invocation.Builder#header(String, Object)
     */
    public RestClient header(String name, Object value) {
        headers.put(name, value);

        return this;
    }

    public RestClient acceptedMediaTypes(MediaType... acceptedMediaTypes) {
        this.acceptedMediaTypes = acceptedMediaTypes;
        return this;
    }

    /**
     * @see javax.ws.rs.client.Invocation.Builder#get(Class)
     */
    public <T> T get(Class<T> responseClass) {
        return method("GET", responseClass, null);
    }

    public <T> T get(GenericType<T> responseType) {

        Response response = method("GET", Response.class, null);

        return response.readEntity(responseType);
    }

    /**
     * Wrapper of {@link #get(Class)} class which decodes a JSON collection.
     *
     * @param responseClass the type of the individual collection objects
     * @return a list of objects
     */
    public <T> List<T> getList(final Class<T> responseClass) {

        return get(new GenericType<List<T>>(new ParameterizedListType (responseClass)));
    }

    /**
     * @see javax.ws.rs.client.Invocation.Builder#post(javax.ws.rs.client.Entity, Class)
     */
    public <T> T post(Class<T> responseClass, Object requestEntity) {
        return method("POST", responseClass, requestEntity);
    }


    public <T> T post(GenericType<T> responseType, Object requestEntity) {

        Response response = method("POST", Response.class, requestEntity);

        return response.readEntity(responseType);
    }

    /**
     * @see javax.ws.rs.client.Invocation.Builder#put(javax.ws.rs.client.Entity, Class)
     */
    public <T> T put(Class<T> responseClass, Object requestEntity) {
        return method("PUT", responseClass, requestEntity);
    }

    public <T> T delete(Class<T> responseClass) {
        return method("DELETE", responseClass, null);
    }

    public void registerFilter(ClientRequestFilter filter) {
        webResource.register(filter);
    }

    private <T> T method(String method, Class<T> responseClass, Object requestEntity) {

        Invocation.Builder builder = webResource.request(acceptedMediaTypes);

        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        long startTime = System.currentTimeMillis();


        Entity entity = null;
        if (requestEntity instanceof Entity) {
            entity = (Entity) requestEntity;
        } else if (requestEntity != null) {
            entity = Entity.entity(requestEntity, contentType);
        }
        Response clientResponse = builder.method(method, entity, Response.class);

        if (logger.isDebugEnabled()) {
            logger.debug("Executed \"{} {}\" {} in {} ms.",
                    new Object[]{method, webResource.getUri().toString(), clientResponse.getStatus(),
                            System.currentTimeMillis() - startTime});
        } else {
            logger.info("Executed \"{} {}://{}{}[?***]\" {} in {} ms.",
                    new Object[]{method, webResource.getUri().getScheme(), webResource.getUri().getHost(),
                            webResource.getUri().getPath(),
                            clientResponse.getStatus(),
                            System.currentTimeMillis() - startTime});
        }

        if (responseClass.isAssignableFrom(Response.class)) {
            return (T) clientResponse;
        }

        return handleResponse(clientResponse, responseClass);
    }

    private <T> T handleResponse(Response clientResponse, Class<T> responseClass) {
        if (clientResponse.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            if (clientResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                return null;
            } else {

                return clientResponse.readEntity(responseClass);
            }
        } else //if (clientResponse.getClientResponseStatus().getFamily().equals(Response.Status.Family.SERVER_ERROR))
        {
            throw new RestCallError(webResource.getUri().toString(), clientResponse);
        }
    }

    public RestClient contentType(MediaType contentType) {
        this.contentType = contentType;
        return this;
    }
}

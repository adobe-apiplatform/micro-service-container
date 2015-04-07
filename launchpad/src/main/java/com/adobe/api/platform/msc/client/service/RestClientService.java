package com.adobe.api.platform.msc.client.service;

import com.adobe.api.platform.msc.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * User: maftei
 * Date: 17/03/15
 */
@Component
public class RestClientService {

    @Autowired
    private Client restClient;

    public RestClient getRestClient(UriBuilder endpoint) {
        return new RestClient(restClient.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }

    public RestClient getRestClient(String endpoint) {
        return new RestClient(restClient.target(endpoint)).acceptedMediaTypes(MediaType.APPLICATION_JSON_TYPE);
    }
}
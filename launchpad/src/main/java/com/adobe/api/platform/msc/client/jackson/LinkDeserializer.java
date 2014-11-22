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
 * suppliers and are protected by all applicable intellectual property
 * laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 ******************************************************************************/

package com.adobe.api.platform.msc.client.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import javax.ws.rs.core.Link;
import java.io.IOException;

/**
 * Custom Jackson deserializer for JAX-RS {@link javax.ws.rs.core.Link} objects.
 * <p/>
 * User: ccristia
 * Date: 01/17/14
 */
public class LinkDeserializer extends JsonDeserializer<Link> {

    @Override
    public Link deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        return Link.fromUri(node.get("href").asText())
                .rel(node.get("rel").asText())
                .title(node.get("title").asText())
                .build();
    }
}

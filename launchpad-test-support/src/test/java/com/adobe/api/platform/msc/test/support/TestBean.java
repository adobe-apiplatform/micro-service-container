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

package com.adobe.api.platform.msc.test.support;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristian Constantin
 * @since 11/24/14
 */
public class TestBean implements Serializable {

    private String name;

    @JsonProperty("_links")
    private List<Link> links = new ArrayList<>();

    public TestBean() {
    }

    public TestBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}

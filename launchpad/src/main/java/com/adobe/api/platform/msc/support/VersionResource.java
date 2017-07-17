package com.adobe.api.platform.msc.support;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Properties;

/**
 * @author vdatcu@dobe.com
 */
@Produces(MediaType.TEXT_PLAIN)
@Path("/version")
@JaxRsComponent
public class VersionResource {


    @GET
    public String getVersion() throws IOException {
        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        String version = properties.getProperty("version");
        if (version == null) {
            version = "UNDEFINED";
        }
        return version;
    }
}

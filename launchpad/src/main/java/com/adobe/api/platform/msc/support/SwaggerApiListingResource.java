package com.adobe.api.platform.msc.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author vdatcu@dobe.com
 */
@SwaggerDefinition(
        info = @Info(
                description = "API Platform Microservices Container",
                version = "V1.0.2",
                title = "API Platform Microservices Container",
                termsOfService = "build-powerful-apis",
                contact = @Contact(name = "API Platform", email = "apiplatform@adobe.com", url = "http://adobe.io"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org")
        ),
        consumes = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS}
)
@Path("/api-docs")
@Api("/api-docs")
@Produces({"application/json"})
@JaxRsComponent
public class SwaggerApiListingResource extends ApiListingResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        logger.info("Initializing Swagger ... ");
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setBasePath("api");
        String[] packageNames = {
                "com.adobe.api.platform.msc",
                "io.swagger.jaxrs.listing",
                "io.swagger.resources"};
        beanConfig.setResourcePackage(String.join(",", packageNames));
        beanConfig.setScan(true);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @ApiOperation(value = "The swagger definition in JSON", hidden = true)
    public Response getListingJson(
            @Context Application app,
            @Context ServletConfig sc,
            @Context HttpHeaders headers,
            @Context UriInfo uriInfo) throws JsonProcessingException {
        return super.getListing(app, sc, headers, uriInfo, "json");
    }

}

micro-service-container
=======================
A Java micro-services container based on Undertow, RESTEasy and Spring Boot.

### Maven setup

Micro-services implementation only need to inherit the `com.adobe.api.platform.msc:micro-service-container-parent` Maven POM and implement JAX-RS resource classes.

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.adobe.api.platform</groupId>
        <artifactId>micro-service-container-parent</artifactId>
        <version>1.0.3</version>
        <relativePath/>
    </parent>

    <groupId>com.adobe.api.platform.sample</groupId>
    <artifactId>sample-micro-service</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>API Platform Sample micro service</name>

    ...
</project>
```

##### Configure version endpoint
To display information about your application version, add **buildnumber-maven-plugin** plugin to the build:

``` 
	<build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>buildnumber-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

 Also, add the following properties to your `application.properties` file:
 ```
 application.name=@project.artifactId@
 build.version=@project.version@
 build.timestamp=@timestamp@
 build.number=@buildNumber@
 ```
where 
* **timestamp** and **buildNumber** are buildnumber-maven-plugin properties
* **project.artifactId** and **project.version**  are predefined maven properties


### Running the service

The Maven build will then generate a single executable JAR file which will contain the micro-service code and the required libraries.

Use `java -jar target/sample-micro-service-1.0-SNAPSHOT.jar` to run the service.

### Exposing REST services

The container recognizes JAX-RS resources and exposes them under the `/api` context.

```java
import com.adobe.api.platform.msc.support.JaxRsComponent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@JaxRsComponent
@Produces(MediaType.APPLICATION_JSON)
@Path("/test")
public class Resource {

    @Autowired
    private MyService myService;

    @GET
    public SampleBean get() {

        return new SampleBean()
    }
}
```

Run `curl http://localhost:8080/api/test` to test the sample service.

The key is to annotate the JAX-RS resources with `com.adobe.api.platform.msc.support.JaxRsComponent`.
Also, the container defines a root Spring context which scans the classpath for any Spring components under the `com.adobe.api.platform` package.

For more Spring fine-tunning you can define another Spring Configuration class.

The JAX-RS component are managed by Spring so you can inject any Spring components using `@Autowired`.

### Configuration

The container reads any configuration properties it find in the `application.properties` file. Possible locations:
- A `/config` subdir of the current directory.
- The current directory
- A classpath `/config` package
- The classpath root 

The list is ordered by precedence (locations higher in the list override lower items).

### Integration tests

Writing integration tests is very easy. The MSC container will be started on port 50000 for each integration test and you can use the provided HTTP client to test the REST services.

You just need to extend a MSC container class, `com.adobe.api.platform.msc.test.BaseTest`.

```java
import com.adobe.api.platform.msc.test.BaseTest;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class IntegrationTest extends BaseTest {

    @Test
    public void test() {

        SampleBean sampleBean = getRestClient()
                .path("test")
                .get(SampleBean.class);

        assertNotNull(sampleBean);
    }
}
```





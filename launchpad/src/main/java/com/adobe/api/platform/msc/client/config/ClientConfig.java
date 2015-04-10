package com.adobe.api.platform.msc.client.config;

import com.adobe.api.platform.msc.client.jackson.JacksonConfig;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: maftei
 * Date: 17/03/15
 */
@Configuration
public class ClientConfig {

    /**
     * HTTP persistent connection pool size (used by resteasy web client).
     */
    @Value("${http.connection_pool.size:}")
    private Integer connectionPoolSize;

    @Value("${http.connection.ttl:}")
    private Integer connectionTTL;

    /**
     * Thread pool size (for async operations).
     */
    @Value("${worker.thread_pool.size:}")
    private Integer workerThreadPooSize;

    /**
     * Used for creating http connections to 3rd party API. It's a heavy-weight object and it's useful to reuse it.
     */
    @Bean
    public Client getJaxrsClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }};

            // Ignore differences between given hostname and certificate hostname
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);

            ResteasyClientBuilder builder = new ResteasyClientBuilder()
                    .sslContext(ctx)
                    .register(JacksonConfig.class)
                    .hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.ANY);

            //if not set, builder sets a pool with 10 threads
            if (workerThreadPooSize != null) {
                // if necessary expose the thread pool as bean (reuse the same thread pool for other scenarios).
                builder.asyncExecutor(Executors.newFixedThreadPool(workerThreadPooSize));
            }

            if (connectionPoolSize != null) {
                builder.connectionPoolSize(connectionPoolSize);
            }

            if (connectionTTL != null) {
                builder.connectionTTL(connectionTTL, TimeUnit.SECONDS);
            }

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example.parser.configuration;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticConfig extends ElasticsearchConfiguration {

    @NotNull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedToLocalhost()
                .usingSsl(buildSSLContext())
                .withBasicAuth("elastic", "h-0BFkfv9QZZERPV=Irt")
                .build();
    }

    private SSLContext buildSSLContext() {
        try{
            return new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

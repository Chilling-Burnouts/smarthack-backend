package com.chillingburnouts.smarthack.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${veridion.api.header}")
    private String apiKeyHeader;

    @Value("${veridion.api.key}")
    private String matchApiKey;
    @Value("${veridion.api.key.search}")
    private String searchApiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                if(template.url().contains("/search")){
                    template.header(apiKeyHeader, searchApiKey);
                }else if(template.url().contains("/match")){
                    template.header(apiKeyHeader, matchApiKey);
                }
            }
        };
    }
}

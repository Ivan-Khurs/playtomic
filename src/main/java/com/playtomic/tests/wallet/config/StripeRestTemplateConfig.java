package com.playtomic.tests.wallet.config;

import com.playtomic.tests.wallet.handler.StripeRestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class StripeRestTemplateConfig {

    @Bean
    public RestTemplate buildRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.errorHandler(new StripeRestTemplateResponseErrorHandler())
                                  .build();
    }
}

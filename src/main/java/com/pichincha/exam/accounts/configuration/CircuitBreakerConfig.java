package com.pichincha.exam.accounts.configuration;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public ReactiveCircuitBreaker reactiveCircuitBreaker(ReactiveCircuitBreakerFactory factory) {
        return factory.create("default");
    }

}

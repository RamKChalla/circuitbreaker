package com.circuitbreaker.config;

import com.circuitbreaker.interceptor.CircuitBreakerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * CircuitBreakerConfiguration.java
 *
 * implementor of this library can extend this configuration and override
 * the circuitBreakerConfiguration bean to add more NoFailureExceptions
 *
 * So that circuitbreaker interceptor may not add failure count for the list of skipexceptions.
 * and the interceptor bean can be initialized with no of failures threshhold and wait time
 */

@Configuration
public class CircuitBreakerConfiguration {
    @Bean
    CircuitBreakerInterceptor circuitBreakerInterceptor(){
        CircuitBreakerInterceptor circuitBreakerInterceptor = new CircuitBreakerInterceptor(10, 500);
        List<Class<? extends Throwable>> noFailureExceptions = new ArrayList<>();
        noFailureExceptions.add(java.lang.IllegalArgumentException.class);
        circuitBreakerInterceptor.setNoFailureExceptions(noFailureExceptions);
        return circuitBreakerInterceptor;
    }

}

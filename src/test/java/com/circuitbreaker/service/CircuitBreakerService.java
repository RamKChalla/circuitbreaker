package com.circuitbreaker.service;

import com.circuitbreaker.ApplyCircuitBreaker;

public interface CircuitBreakerService {

    public static final String CIRCUIT_BREAKER_NAME = "CB";

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER_NAME, failureThreshold = 10, timeUntilRetry = 1000)
    public void withoutaIssue();

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER_NAME, failureThreshold = 20, timeUntilRetry = 300)
    public void withAIssue();

}

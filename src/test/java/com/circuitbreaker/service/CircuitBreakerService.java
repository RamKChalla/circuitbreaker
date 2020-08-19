package com.circuitbreaker.service;

import com.circuitbreaker.ApplyCircuitBreaker;

public interface CircuitBreakerService {

    public static final String CIRCUIT_BREAKER = "CB";
    public static final String CIRCUIT_BREAKER_ISSUE = "CB_ISSUE";

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER, failureThreshold = 10, timeUntilRetry = 1000)
    public void withoutaIssue();

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER_ISSUE, failureThreshold = 2, timeUntilRetry = 2)
    public void withAIssue();

}

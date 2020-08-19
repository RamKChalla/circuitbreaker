package com.circuitbreaker.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.circuitbreaker.ApplyCircuitBreaker;
import com.circuitbreaker.exception.BreakingException;

public class CircuitBreakerServiceImpl implements CircuitBreakerService{
    private final transient Log log = LogFactory.getLog(getClass());
    
    public static final String CIRCUIT_BREAKER = "CB";
    public static final String CIRCUIT_BREAKER_ISSUE = "CB_ISSUE";

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER, failureThreshold = 20, timeUntilRetry = 300)
    public void withoutaIssue()
    {
        log.info("without problems");
    }

    @ApplyCircuitBreaker(name=CIRCUIT_BREAKER_ISSUE, failureThreshold = 20, timeUntilRetry = 300)
    public void withAIssue()
    {
        throw new BreakingException("breaking exception!");
    }

}

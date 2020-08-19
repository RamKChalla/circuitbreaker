package com.circuitbreaker.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CircuitBreakerServiceImpl implements CircuitBreakerService{
    private final transient Log log = LogFactory.getLog(getClass());

    public void withoutaIssue()
    {
        log.info("without problems");
    }

    public void withAIssue()
    {
        throw new BreakingException("breaking exception!");
    }


    public static class BreakingException extends RuntimeException
    {
        public BreakingException(final String s)
        {
            super(s);
        }
    }
}

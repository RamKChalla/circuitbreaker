package com.circuitbreaker.exception;

public class BreakingException extends RuntimeException
{
    public BreakingException(final String s)
    {
        super(s);
    }
}

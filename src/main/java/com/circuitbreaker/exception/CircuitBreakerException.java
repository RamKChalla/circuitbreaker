package com.circuitbreaker.exception;

/**
 * CirucitBreakerException.java can thrown in case any of the service methods fails.
 */
public class CircuitBreakerException extends  Throwable{
    public CircuitBreakerException() {
    }

    public CircuitBreakerException(String message) {
        super(message);
    }

    public CircuitBreakerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircuitBreakerException(Throwable cause) {
        super(cause);
    }

    public CircuitBreakerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

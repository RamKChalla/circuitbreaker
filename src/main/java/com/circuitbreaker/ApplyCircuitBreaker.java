package com.circuitbreaker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApplyCircuitBreaker.java
 *  is custom annotation this can be applied to all the methods of implementation of project
 *  So that the interceptor can circuitbreak for failures.
 *
 *  we can use name, failureThreshHold and timeUntilRetry
 *
 *  This can be enhanced to use for user/machine level threshhold
 *
 *  for this project purpose - have implemented or used name for method name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApplyCircuitBreaker {
    public static final int TIMEOUT = 1000;
    public static final int MAX_FAILURES = 3;

    /**
     * 1s max execution time of any public method of the monitored class.
     *
     * @return The overridden maximal execution time. Slower calls will increase
     * the counter and eventually close the circuit.
     */
    long timeUntilRetry() default TIMEOUT;

    /**
     * The maximal number of failures (exception occurrences and timeouts)
     * before the circuit opens.
     *
     * @return the threshold which closes the circuit.
     */
    int failureThreshold() default MAX_FAILURES;

    /**
     * name of the method / unique identifer
     * @return
     */
    String name() default "None";
}
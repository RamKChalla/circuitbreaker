package com.circuitbreaker;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author Ram Challa (Maddela)
 * CircuiteBreaker has two states ON/OFF
 * ON MEANS - execute the method
 * OFF MEANS - Stopped the exeuction due to no.of failures threshhold
 *  after waittime exceeds, CircuitBreaker will be on executes the method calls.
 */
public class CircuitBreaker
{
    private final long failureThreshold;
    private AtomicLong failureCount = new AtomicLong(0);
    private final long timeUntilRetry;
    private CircuitBreakerStatus status;
    private long lastOpenedTime;
    private final String name;

    /**
     *
     * @param name
     * @param failureThreshold
     * @param timeUntilRetry
     *
     * name of the method, no of failures and time until retry after time exceeds
     *
     */
    public CircuitBreaker(String name, int failureThreshold, int timeUntilRetry)
    {
        this.name = name;
        this.failureThreshold = failureThreshold;
        this.timeUntilRetry = timeUntilRetry;
        this.status = CircuitBreakerStatus.ON;
    }

    public String getName()
    {
        return name;
    }

    public void setStatus(final CircuitBreakerStatus status)
    {
        this.status = status;
    }

    public CircuitBreakerStatus getStatus()
    {
        return status;
    }

    public void setLastOpenedTime(final long lastOpenedTime)
    {
        this.lastOpenedTime = lastOpenedTime;
    }

    public long getLastOpenedTime()
    {
        return lastOpenedTime;
    }

    //add failure count
    public void addFailure()
    {
        failureCount.incrementAndGet();
    }

    //set failure count for tracking no.of failures
    private void setFailureCount(final AtomicLong failureCount)
    {
        this.failureCount = failureCount;
    }

    public AtomicLong getFailureCount()
    {
        return failureCount;
    }

    /**
     * check wait time exceeded
     * @return
     */
    public boolean isWaitTimeExceeded()
    {
        return System.currentTimeMillis() - timeUntilRetry > lastOpenedTime;
    }

    /**
     * check threshhold
     * @return
     */
    public boolean isThresholdReached()
    {
        return getFailureCount().get() >= getFailureThreshold();
    }

    public long getFailureThreshold()
    {
        return failureThreshold;
    }

    public void open()
    {
        setLastOpenedTime(System.currentTimeMillis());
        setFailureCount(new AtomicLong(0));
        setStatus(CircuitBreakerStatus.ON);
    }

    public void off()
    {
        setStatus(CircuitBreakerStatus.OFF);
    }

    public boolean isOn()
    {
        return getStatus() == CircuitBreakerStatus.ON;
    }

    public boolean isOff()
    {
        return getStatus() == CircuitBreakerStatus.OFF;
    }

}  

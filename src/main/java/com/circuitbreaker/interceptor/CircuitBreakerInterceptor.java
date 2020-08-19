package com.circuitbreaker.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.circuitbreaker.ApplyCircuitBreaker;
import com.circuitbreaker.CircuitBreaker;
import com.circuitbreaker.exception.CircuitBreakerException;
import com.circuitbreaker.exception.HttpClientException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author Ram Challa (Maddela)
 *  CircuitBreaker has two states : ON, OFF
 *  ON means - method execution go through
 *  OFF means -
 *     stop the method execution on a certain number of failures and for certain period of time.
 *  Using annotation to configure CircuitBreaker using @ApplyCircuitBreaker
 */
public class CircuitBreakerInterceptor implements MethodInterceptor
{
    private Map<String, CircuitBreaker> breakers = new HashMap<>();
    private transient final Log log = LogFactory.getLog(getClass());
    private List<Class<? extends Throwable>> noFailureExceptions;
    private final int failureThreshold;
    private final int timeUntilRetry;

    public CircuitBreakerInterceptor(int failureThreshold, int timeUntilRetry)
    {
        this.failureThreshold = failureThreshold;
        this.timeUntilRetry = timeUntilRetry;
    }

    public CircuitBreaker getBreaker(String name)
    {
        if (breakers.get(name) == null)
        {
            breakers.put(name, new CircuitBreaker(name, failureThreshold, timeUntilRetry));
        }
        return breakers.get(name);
    }

    private boolean isaNonFailureException(Throwable t)
    {
        for (Class throwable : noFailureExceptions)
        {
            if (throwable.isAssignableFrom(t.getClass()))
            {
                return true;
            }
        }
        return false;
    }

    public List<Class<? extends Throwable>> getNoFailureExceptions
            ()
    {
        if (noFailureExceptions == null)
            noFailureExceptions = new ArrayList<Class<? extends Throwable>>();
        return noFailureExceptions;
    }

    public void setNoFailureExceptions(final List<Class<? extends Throwable>> noFailureExceptions)
    {
        this.noFailureExceptions = noFailureExceptions;
    }

    public Object invoke(final MethodInvocation invocation) throws Throwable
    {
        log.info("Going through circuitbreaker protected method");
        ApplyCircuitBreaker breakerAnnot = invocation.getMethod().getAnnotation(ApplyCircuitBreaker.class);
        if (breakerAnnot != null)
        {
            // get the method name / name from the annotation to track the no.of failures and waittime
            CircuitBreaker breaker = getBreaker(breakerAnnot.name());
            Object returnValue = null;

            //First check if the breaker is off - means calls not going through
            if (breaker.isOff())
            {
                //check if the wait time exceeded in case of breaker off
                if (breaker.isWaitTimeExceeded())
                {
                    try
                    {
                        //open the breaker to let the method execution
                        breaker.open();
                        log.info("Retrying operation " + invocation.getMethod().toGenericString() + " after waitTime exceeded");
                        returnValue = invocation.proceed();
                        log.info("Retry of operation " + invocation.getMethod().toGenericString() + " succeeded, resetting circuit breaker");

                    }
                    catch (Throwable t)
                    {
                        // in case exection - check if the exception is not considered as a failure
                        // check if the excetion is not a 4XX exception
                        // if both the above conditions are false that means its a failure
                        // increase the failure count and check for the threshold to off the circuit breaker.
                        if (!isaNonFailureException(t) && !checkForClient4xxExceptions(t))
                       {
                           handleFailuresNThreshhold(invocation, breaker, t);
                       }
                    }
                }
                else
                {
                    throw new CircuitBreakerException("This operation cannot be performed due to open circuit breaker (too many failures).");
                }
            }
            else if (breaker.isOn())
            {
                try
                {
                    returnValue = invocation.proceed();
                }
                catch (Throwable t)
                {
                    // in case exection - check if the exception is not considered as a failure
                    // check if the excetion is not a 4XX exception
                    // if both the above conditions are false that means its a failure
                    // increase the failure count and check for the threshold to off the circuit breaker.
                    log.info("Breaker is still Failure of operation " + invocation.getMethod().toGenericString() + " in circuit breaker", t);
                    if (!isaNonFailureException(t) && !checkForClient4xxExceptions(t))
                    {
                        handleFailuresNThreshhold(invocation, breaker, t);
                    }
                }
            }

            return returnValue;
        }
        else
        {
            return invocation.proceed();
        }
    }

    /**
     *
     * @param invocation
     * @param breaker
     * @param t
     * @throws CircuitBreakerException
     *
     * increase a failure count and checks if the failure count > threshhold to stop the exeuction
     * and stops (off) the method execution.
     *
     */


    private void handleFailuresNThreshhold(MethodInvocation invocation, CircuitBreaker breaker, Throwable t) throws CircuitBreakerException {
        breaker.addFailure();
        if (breaker.isThresholdReached()) {
            log.error("Circuit breaker tripped on operation " + invocation.getMethod().toGenericString() + " failure.", t);
            breaker.off();
            throw new CircuitBreakerException("The operation " + invocation.getMethod().toGenericString() + " has too many failures, tripping circuit breaker.", t);
        }
    }

    /**
     *
     * @param t
     * @return
     * @throws Throwable
     *
     * checks for 4xx exception to not to increase the failure count
     */
    private boolean checkForClient4xxExceptions(Throwable t) throws Throwable {
        if(t instanceof HttpClientException)
        {
            HttpClientException ex = (HttpClientException) t;
            if(ex.getStatusCode().is4xxClientError()){
                return true;
            }
        }
        return false;
    }
}
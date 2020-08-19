# CircutBreaker project

* Developed using spring AOP, this can be done in different ways.

* as per the requirement - Circuitbreaker has two states (ON, OFF)
* ON - Allow method execution
* OFF - Do Not Allow method execution

* for OFF/TRIP
   * The no.of failures for a call to specific method exceeds the threshold
* for ON
   * if the method name doesn't exist in the Map
   * if waittime > (timeUntilRetry) 

## Build
* mvn clean install
* creates a jar file, can used as a dependency in your project
* use @ApplyCircuitBreaker annotation on the method that you want to apply circuitbreaker
* Use this dependency in your implementation project
* this circuit breaker can be used with any method (Standalone or REST)
            
* 		<dependency>
  			<groupId>com.cicuitbreaker</groupId>
  			<artifactId>cirucitbreaker</artifactId>
  			<version>1.0.0</version>
  		</dependency>
  		
* Example: `@ApplyCircuitBreaker(name="MethodName", failureThreshold = 20, timeUntilRetry = 300)`   
            `public void withoutaIssue();`
            
* <b>issue</b> - used Intellij community edition to develo this project, community edition doesn't support Spring. This project can be enhance with retry etc.,

### Added Tests
* TestForWithAIssueCase.java
` After configured no of failures - we get this error
Exception in thread "main" com.circuitbreaker.exception.CircuitBreakerException: The operation public void com.circuitbreaker.service.CircuitBreakerServiceImpl.withAIssue() has too many failures, tripping circuit breaker.
	at com.circuitbreaker.interceptor.CircuitBreakerInterceptor.handleFailuresNThreshhold(CircuitBreakerInterceptor.java:161)
	at com.circuitbreaker.interceptor.CircuitBreakerInterceptor.invoke(CircuitBreakerInterceptor.java:130)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)
	at com.circuitbreaker.service.CircuitBreakerServiceImpl$$EnhancerBySpringCGLIB$$e829a3b7.withAIssue(<generated>)
	at com.circuitbreaker.app.TestForWithAIssueCase.main(TestForWithAIssueCase.java:49)
`
* TestForWithoutIssueCase.java
* TestForWithAIssueWithNofailureExceptionList.java
    
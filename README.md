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


    
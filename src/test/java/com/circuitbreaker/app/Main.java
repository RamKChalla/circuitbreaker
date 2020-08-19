package com.circuitbreaker.app;

import com.circuitbreaker.config.CircuitBreakerConfiguration;
import com.circuitbreaker.interceptor.CircuitBreakerInterceptor;
import com.circuitbreaker.service.CircuitBreakerService;
import com.circuitbreaker.service.CircuitBreakerServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main.java - Test class written to execute the tests
 */
public class Main {
    public static void main(String[] args){

//        ApplicationContext context
//                = new AnnotationConfigApplicationContext(CircuitBreakerConfiguration.class);
//        CircuitBreakerInterceptor circuitBreakerInterceptor = context.getBean(CircuitBreakerInterceptor.class);
//
        CircuitBreakerService target = new CircuitBreakerServiceImpl();

        NameMatchMethodPointcut pc = new NameMatchMethodPointcut();
        pc.addMethodName("withAIssue");
        pc.addMethodName("withIgnoredProblem");
        pc.addMethodName("withoutProblem");

        Advisor advisor = new DefaultPointcutAdvisor(pc, new CircuitBreakerInterceptor( 2,10));

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        CircuitBreakerServiceImpl proxy = (CircuitBreakerServiceImpl)pf.getProxy();

        proxy.withAIssue();


    }
}

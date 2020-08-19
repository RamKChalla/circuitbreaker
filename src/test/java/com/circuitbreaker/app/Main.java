package com.circuitbreaker.app;

import com.circuitbreaker.config.CircuitBreakerConfiguration;
import com.circuitbreaker.exception.BreakingException;
import com.circuitbreaker.interceptor.CircuitBreakerInterceptor;
import com.circuitbreaker.service.CircuitBreakerService;
import com.circuitbreaker.service.CircuitBreakerServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
        pc.addMethodName("withoutaIssue");
        //pc.addMethodName("withIgnoredProblem");
        pc.addMethodName("withAIssue");

        CircuitBreakerInterceptor interceptor = circuitBreakerInterceptor();
        
        
        Advisor advisor = new DefaultPointcutAdvisor(pc, interceptor);

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        CircuitBreakerService proxy = (CircuitBreakerServiceImpl)pf.getProxy();

        //proxy.withoutaIssue();
        
        for(int i=0; i < 25; i ++) {
        	System.out.println(i);
           proxy.withAIssue();
        }


    }
    
    static CircuitBreakerInterceptor circuitBreakerInterceptor(){
        CircuitBreakerInterceptor circuitBreakerInterceptor = new CircuitBreakerInterceptor(2, 10);
        List<Class<? extends Throwable>> noFailureExceptions = new ArrayList<>();
        noFailureExceptions.add(java.lang.IllegalArgumentException.class);
        //noFailureExceptions.add(BreakingException.class);
        circuitBreakerInterceptor.setNoFailureExceptions(noFailureExceptions);
        return circuitBreakerInterceptor;
    }
}

package com.wealthshrimple.WealthShrimple.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	
	@Around("execution(* com.wealthshrimple.WealthShrimple.controller.UserController.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("****LoggingAspect.logAroundCreateEmployee() : " + joinPoint.getSignature().getName() + ": Before Method Execution");
		Object result = joinPoint.proceed();
		log.info("****LoggingAspect.logAroundCreateEmployee() : " + joinPoint.getSignature().getName() + ": After Method Execution");
		return result;
	}

}

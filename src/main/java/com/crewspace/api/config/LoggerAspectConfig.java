package com.crewspace.api.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggerAspectConfig {

    @Around("execution(* com.crewspace.api.controller.v1.*Controller.*(..)) || execution(* com.crewspace.api.service.*Service.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        String type = "";
        String name = joinPoint.getSignature().getDeclaringTypeName();

        if (name.contains("Controller")) {
            type = "Controller - ";
            log.info(type + name.split(".controller.")[1] + "." + joinPoint.getSignature().getName() + "()");

        } else if (name.contains("Service")) {
            type = "Service - ";
            log.info(type + name.split(".service.")[1] + "." + joinPoint.getSignature().getName() + "()");
        }

        return joinPoint.proceed();
    }
}

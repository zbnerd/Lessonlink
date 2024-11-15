package com.lessonlink.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {
    @Around("@annotation(com.lessonlink.aop.annotation.LogExecutionTime)") // 사용자 정의 어노테이션 대상
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 대상 메서드 실행

        long executionTime = System.currentTimeMillis() - start;

        log.info("{} Execution time : {} ms", joinPoint.getSignature(), executionTime);
        return result;
    }
}

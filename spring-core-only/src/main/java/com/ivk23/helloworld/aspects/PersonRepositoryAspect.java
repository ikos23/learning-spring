package com.ivk23.helloworld.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersonRepositoryAspect {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryAspect.class);

    @Before(value = "execution(* com.ivk23.helloworld.aspects.PersonRepository+.findBy*(..))")
    public void beforeFindBy(JoinPoint joinPoint) {
        final var methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        LOG.info(">>>> Method [{}] will be called.", methodName);
    }

    @AfterReturning(value = "execution(* com.ivk23.helloworld.aspects.PersonRepository+.findBy*(..))")
    public void afterFindBy(JoinPoint joinPoint) {
        final var methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        LOG.info(">>>> Method [{}] has just been called.", methodName);
    }

}

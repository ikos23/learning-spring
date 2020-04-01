package com.ivk23.helloworld.aspects.sample2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

// all the configs are in resources/aop-sample2-conf.xml
public class SimpleAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleAspect.class);

    public Object around(ProceedingJoinPoint pjp) {
        final var methodName = pjp.getSignature().getName();
        final var methodArgs = Arrays.stream(pjp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        LOG.info("Method [{}] with args [{}] is about to be called.", methodName, methodArgs);
        try {
            final var res = pjp.proceed();
            LOG.info("Method [{}] with args [{}] returned [{}].",
                    methodName, methodArgs, res);
            return res;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

}

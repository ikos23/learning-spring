package com.ivk23.helloworld.aspects.sample3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);


    @After("@within(com.ivk23.helloworld.aspects.sample3.Logged)")
    public void log(JoinPoint jp) {
        var m = jp.getSignature().getDeclaringTypeName() + "."
                + jp.getSignature().getName();
        LOG.info("******* {} called. *******", m);
    }

}

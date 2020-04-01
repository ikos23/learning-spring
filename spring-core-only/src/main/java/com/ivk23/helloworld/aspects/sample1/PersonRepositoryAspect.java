package com.ivk23.helloworld.aspects.sample1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * [Aspect] is simply a class containing code-specific to
 * a cross-cutting concern.
 *
 * Spring recognized a class as an aspect
 * if we annotate it with @Aspect.
 *
 * [Join Point] A point during the execution of a program.
 * In Spring AOP it is always a method execution.
 *
 * [Pointcut] A predicate used to identify join points.
 * (makes sense right ? we do not usually want to exec something
 * before of after avery method in every class of the application)
 */
@Aspect
@Component
public class PersonRepositoryAspect {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryAspect.class);

    /**
     * [Advice] The action taken by an aspect at a join point.
     * There are multiple advice types.
     *
     * Here is am example of @Before advice - is executed before
     * every method which matches the pointcut expression (pointcut - where to bind the advice)
     *
     * execution([Modifiers] [ReturnType] [FullClassName].[MethodName]([Args]) throws [ExceptionType])
     *
     * [*] wildcard replaces any group of chars
     * [+] wildcard means we search in subclasses (or class which impl interface) where name starts as [FullClassName]
     *
     * Complex expressions with &&, || are allowed.
     */
    @Before(value = "execution(* com.ivk23.helloworld.aspects.sample1.PersonRepository+.findBy*(..))")
    public void beforeFindBy(JoinPoint joinPoint) {
        final var methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        LOG.info(">>>> Method [{}] is about to be called.", methodName);
    }

    @AfterReturning(value = "execution(* com.ivk23.helloworld.aspects.sample1.PersonRepository+.findBy*(..))")
    public void afterFindBy(JoinPoint joinPoint) {
        final var methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        LOG.info(">>>> Method [{}] has just been called.", methodName);
    }

}

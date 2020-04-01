package com.ivk23.petclinic.kotlin.audit

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class MetricAspect {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(MetricAspect::class.java)
    }

    @Around("metricAnnotated() && anyPublicOperation()")
    fun executionTimeAdvice(pjp: ProceedingJoinPoint): Any? {
        val method = "${pjp.signature.declaringTypeName}.${pjp.signature.name}"
        try {
            val start = System.currentTimeMillis()
            val result = pjp.proceed()
            val timeTaken = (System.currentTimeMillis() - start)
            log.info("[METRIC] $method execution time is $timeTaken millis.")
            return result
        } catch (ex: Throwable) {
            log.warn("[METRIC] $method exception thrown ${ex.javaClass.canonicalName} " +
                    "with message '${ex.message}'")
            throw ex
        }
    }

    @Pointcut("@within(com.ivk23.petclinic.kotlin.audit.Metric)")
    private fun metricAnnotated() {}

    @Pointcut("execution(public * *(..))")
    private fun anyPublicOperation() {}

}
package com.ivk23.petclinic.kotlin.audit

/**
 * If you put this on a class,
 * every method of that class gets logging of
 * how long it is being executed :)
 *
 * ### Example:
 * ```
 *     INFO a.b.c.MetricAspect : [METRIC] a.b.c.MyClass.methodName execution time is 171 millis.
 * ```
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Metric
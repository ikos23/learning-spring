package com.ivk23.helloworld.aspects.sample1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * SPRING AOP
 * <p>
 * The original library that provided components
 * for creating aspects is named AspectJ.
 * <p>
 * The aspects developed in AspectJ are processed at compile time,
 * so they directly affect the generated bytecode.
 * <p>
 * The Spring AOP framework is a complement to the current version of AspectJ
 * and contains many annotations that can develop
 * and configure aspects using Java code.
 * <p>
 * Spring AOP functionality is based on AspectJ
 * which is why when Spring AOP libraries are used,
 * aspectjweaver and aspectjrt must be added to the application classpath.
 * <p>
 * Spring AOP cannot advise objects
 * that are not managed by the Spring container !!!
 */
@Configuration
@ComponentScan(basePackages = "com.ivk23.helloworld.aspects")
// Enables support for handling components marked with AspectJ's @Aspect annotation
@EnableAspectJAutoProxy
public class SpringConfiguration {
}
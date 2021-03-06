package com.ivk23.helloworld.aspects.sample3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.ivk23.helloworld.aspects.sample3")
@EnableAspectJAutoProxy
public class Config {
}

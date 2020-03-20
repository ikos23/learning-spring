package com.ivk23.helloworld.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        System.out.println("Registered beans: " + ctx.getBeanDefinitionNames().length);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        final var personRepository = ctx.getBean(PersonRepository.class);
        LOG.info(">>>> Person found: {}", personRepository.findById(1L));
    }

}

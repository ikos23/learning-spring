package com.ivk23.helloworld.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Sample0 {

    private static final Logger LOG = LoggerFactory.getLogger(Sample0.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        // those two basically do the same under the hood
        ctx.registerShutdownHook();
        ctx.close();
    }
}

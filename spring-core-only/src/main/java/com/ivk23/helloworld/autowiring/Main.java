package com.ivk23.helloworld.autowiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        // Prints:
        // INFO A.exec - $ A.exec() called.
        // INFO B.exec - $ B.exec() calling A.exec().
        // INFO A.exec - $ A.exec() called.
        // INFO B.exec - $ B.exec() call ended.
        // INFO C.exec - $ C.exec() called.
        ctx.getBean(C.class).exec();

        // a small test for message source (see Config.class)
        final var messageSourceBean = (MessageSource) ctx.getBean("myMessageSource");
        System.out.println(messageSourceBean.getClass());
    }
}

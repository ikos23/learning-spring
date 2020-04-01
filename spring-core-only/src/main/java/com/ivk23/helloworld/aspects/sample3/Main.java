package com.ivk23.helloworld.aspects.sample3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// demo
public class Main {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        ctx.getBean(MyService1.class).service();
        ctx.getBean(MyService3.class).service();

        // INFO  c.i.h.a.s.MyService1.service - ***** MyService1.service *****
        // INFO  c.i.h.a.s.LoggingAspect.log - ******* {MyService1.service} called. *******
        // INFO  c.i.h.a.s.MyServicce3.service - ***** MyService3.service *****
    }

}

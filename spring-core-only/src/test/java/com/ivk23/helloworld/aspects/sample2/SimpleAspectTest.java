package com.ivk23.helloworld.aspects.sample2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:aop-sample2-conf.xml")
class SimpleAspectTest {

    @Autowired
    private SimpleBean simpleBean;

    @Test
    void aroundTest() {
        final var foo = simpleBean.sayHello("Foo");
        assertEquals("Hello, Foo", foo);

        // INFO SimpleAspect.around - Method [sayHello] with args [Foo] is about to be called.
        // INFO SimpleBean.sayHello - SimpleAspect.sayHello(Foo) called.
        // INFO SimpleAspect.around - Method [sayHello] with args [Foo] returned [Hello, Foo].
    }


}
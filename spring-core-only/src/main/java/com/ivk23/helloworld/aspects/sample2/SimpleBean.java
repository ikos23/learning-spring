package com.ivk23.helloworld.aspects.sample2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleBean {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleBean.class);

    public String sayHello(String toWhom) {
        LOG.info("SimpleAspect.sayHello({}) called.", toWhom);
        return "Hello, " + toWhom;
    }

}

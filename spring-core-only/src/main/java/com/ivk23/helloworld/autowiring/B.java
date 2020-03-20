package com.ivk23.helloworld.autowiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class B {

    private static final Logger LOG = LoggerFactory.getLogger(B.class);

    private final A a;

    // single constructor, so even @Autowire is optional here
    public B(A a) {
        this.a = a;
    }

    public void exec() {
        LOG.info("$ B.exec() calling A.exec().");
        a.exec();
        LOG.info("$ B.exec() call ended.");
    }
}

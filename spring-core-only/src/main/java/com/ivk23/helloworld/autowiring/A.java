package com.ivk23.helloworld.autowiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class A {

    private static final Logger LOG = LoggerFactory.getLogger(A.class);

    public void exec() {
        LOG.info("$ A.exec() called.");
    }

}

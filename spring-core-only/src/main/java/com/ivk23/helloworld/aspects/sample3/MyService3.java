package com.ivk23.helloworld.aspects.sample3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService3 {

    private static final Logger LOG = LoggerFactory.getLogger(MyService3.class);

    public void service() {
        LOG.info("***** MyService3.service *****");
    }

}

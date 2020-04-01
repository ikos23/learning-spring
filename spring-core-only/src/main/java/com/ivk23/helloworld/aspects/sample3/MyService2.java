package com.ivk23.helloworld.aspects.sample3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Logged
@Service
public class MyService2 {

    private static final Logger LOG = LoggerFactory.getLogger(MyService2.class);

    public void service() {
        LOG.info("***** MyService2.service *****");
    }

}

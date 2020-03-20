package com.ivk23.helloworld.newway;

import com.ivk23.helloworld.newway.components.SimpleBFPP;
import com.ivk23.helloworld.newway.components.SimpleBPP;
import com.ivk23.helloworld.newway.components.SimpleBean;
import com.ivk23.helloworld.newway.components.SimpleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimpleConfig.class, SimpleBFPP.class, SimpleBPP.class);
        LOG.info("Available Beans: {}", ctx.getBeanDefinitionNames().length);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        LOG.info("SimpleBean: {}", ctx.getBean("simpleBean", SimpleBean.class));
        LOG.info("SimpleBean2: {}", ctx.getBean("simpleBean2", SimpleBean.class));
    }
}

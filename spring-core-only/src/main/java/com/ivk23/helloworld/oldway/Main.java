package com.ivk23.helloworld.oldway;

import com.ivk23.helloworld.oldway.components.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("my-beans.xml");
        System.out.println("Available Beans: " + ctx.getBeanDefinitionNames().length);

        final DataSource bean = ctx.getBean(DataSource.class);
        System.out.println("Bean returns: " + bean.getConnection());
    }
}

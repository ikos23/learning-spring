package com.ivk23.helloworld.scopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        LOG.info("--- 1 --------------------------------------");

        LOG.info(ctx.getBean(SingletonBean.class).toString());
        LOG.info(ctx.getBean(SingletonBean.class).toString());

        // Two lines above ^ produces expected output:
        // -------------------------------------------
        // TRACE DefaultListableBeanFactory.doGetBean - Returning cached instance of singleton bean 'singletonBean'
        // INFO  Main.main - SingletonBean {id=702}
        // TRACE DefaultListableBeanFactory.doGetBean - Returning cached instance of singleton bean 'singletonBean'
        // INFO  Main.main - SingletonBean {id=702}

        LOG.info("--- 2 --------------------------------------");

        LOG.info(ctx.getBean(PrototypeBean.class).toString());
        LOG.info(ctx.getBean(PrototypeBean.class).toString());

        // Two lines above ^ produces the following output:
        // TRACE DefaultListableBeanFactory.createBean - Creating instance of bean 'prototypeBean'
        // TRACE DefaultListableBeanFactory.createBean - Finished creating instance of bean 'prototypeBean'
        // INFO  Main.main - PrototypeBean {id=865, value=-1}
        // TRACE DefaultListableBeanFactory.createBean - Creating instance of bean 'prototypeBean'
        // TRACE DefaultListableBeanFactory.createBean - Finished creating instance of bean 'prototypeBean'
        // INFO  Main.main - PrototypeBean {id=946, value=-1}

        LOG.info("--- 3 --------------------------------------");

        LOG.info(ctx.getBean(ComplexSingletonBean.class).toString());
        LOG.info(ctx.getBean(ComplexSingletonBean.class).toString());

        // The dependency (is prototype !!!) is the same every time, even though is prototype !
        // TRACE DefaultListableBeanFactory.doGetBean - Returning cached instance of singleton bean 'complexSingletonBean'
        // INFO  Main.main - ComplexSingletonBean {id=536, prototypeBean=PrototypeBean {id=172, value=-1}}
        // TRACE DefaultListableBeanFactory.doGetBean - Returning cached instance of singleton bean 'complexSingletonBean'
        // INFO  Main.main - ComplexSingletonBean {id=536, prototypeBean=PrototypeBean {id=172, value=-1}}

        LOG.info("--- 4 --------------------------------------");

        LOG.info(ctx.getBean(AnotherComplexSingletonBean.class).toString());
        LOG.info(ctx.getBean(AnotherComplexSingletonBean.class).toString());

        // Finally :)
        // INFO Main.main - AnotherComplexSingletonBean {id=9, prototypeBean=SimpleJavaBean {id=544, value=-1}}
        // INFO Main.main - AnotherComplexSingletonBean {id=9, prototypeBean=SimpleJavaBean {id=529, value=-1}}
    }
}

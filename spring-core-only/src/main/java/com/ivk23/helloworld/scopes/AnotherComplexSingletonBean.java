package com.ivk23.helloworld.scopes;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AnotherComplexSingletonBean {

    private final int id;
    private final SimpleJavaBean prototypeBean;

    public AnotherComplexSingletonBean(SimpleJavaBean simpleJavaBean) {
        this.id = new Random().nextInt(1000);
        this.prototypeBean = simpleJavaBean;
    }

    @Override
    public String toString() {
        return "AnotherComplexSingletonBean {" +
                "id=" + id +
                ", prototypeBean=" + prototypeBean +
                '}';
    }
}

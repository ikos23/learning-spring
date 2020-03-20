package com.ivk23.helloworld.scopes;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ComplexSingletonBean {

    private final int id;
    private final PrototypeBean prototypeBean;

    public ComplexSingletonBean(PrototypeBean prototypeBean) {
        this.id = new Random().nextInt(1000);
        this.prototypeBean = prototypeBean;
    }

    @Override
    public String toString() {
        return "ComplexSingletonBean {" +
                "id=" + id +
                ", prototypeBean=" + prototypeBean +
                '}';
    }
}

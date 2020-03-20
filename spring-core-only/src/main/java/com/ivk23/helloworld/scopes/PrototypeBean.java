package com.ivk23.helloworld.scopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope(value = "prototype")
public class PrototypeBean {

    private final int id;
    private final int value = -1;

    public PrototypeBean() {
        this.id = new Random().nextInt(1000);
    }

    @Override
    public String toString() {
        return "PrototypeBean {" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

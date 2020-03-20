package com.ivk23.helloworld.scopes;

import org.springframework.stereotype.Component;

import java.util.Random;

// by default scope is @Scope("singleton"),
// so we do not specify anything here
@Component
public class SingletonBean {

    private final int id;

    public SingletonBean() {
        this.id = new Random().nextInt(1000);
    }

    @Override
    public String toString() {
        return "SingletonBean {" +
                "id=" + id +
                '}';
    }
}

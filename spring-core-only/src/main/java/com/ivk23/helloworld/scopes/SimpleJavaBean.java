package com.ivk23.helloworld.scopes;

import java.util.Random;


public class SimpleJavaBean {

    private final int id;
    private final int value = -1;

    public SimpleJavaBean() {
        this.id = new Random().nextInt(1000);
    }

    @Override
    public String toString() {
        return "SimpleJavaBean {" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

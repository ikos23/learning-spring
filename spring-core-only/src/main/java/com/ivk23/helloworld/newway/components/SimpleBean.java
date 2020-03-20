package com.ivk23.helloworld.newway.components;

import org.springframework.stereotype.Component;

@Component
public class SimpleBean {

    private String field;

    private String someProps;

    public SimpleBean() {
    }

    public SimpleBean(String field, String someProps) {
        this.field = field;
        this.someProps = someProps;
    }

    public void initMe() {
        this.field = "INIT";
        this.someProps = "INIT";
        System.out.println("INIT ME CALLED !!!!!!");
    }

    public String getField() {
        return field;
    }

    public String getSomeProps() {
        return someProps;
    }

    @Override
    public String toString() {
        return "SimpleBean {" +
                "field='" + field + '\'' +
                ", someProps='" + someProps + '\'' +
                '}';
    }
}

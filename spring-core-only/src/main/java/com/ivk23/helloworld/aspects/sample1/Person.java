package com.ivk23.helloworld.aspects.sample1;

public class Person {

    private Long id;
    private String fullName;

    public Person(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Person {" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

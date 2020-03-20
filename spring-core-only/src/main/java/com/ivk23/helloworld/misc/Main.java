package com.ivk23.helloworld.misc;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepoProxy = (UserRepository)Proxy.newProxyInstance(Main.class.getClassLoader(),
                new Class<?>[]{UserRepository.class},
                ((proxy, method, args1) -> {
                    return "I am Proxy.";
                }));

        System.out.println(userRepoProxy.find());

        UserRepository repo1 = new UserRepositoryImpl();
        UserRepository repo2 = new UserRepositoryImpl();

        userRepoProxy = (UserRepository)Proxy.newProxyInstance(Main.class.getClassLoader(),
                new Class<?>[]{UserRepository.class},
                ((proxy, method, args1) -> {
                    System.out.println("Before method call.");
                    final var res = method.invoke(repo1);
                    System.out.println("After method call.");
                    return res;
                }));

        System.out.println("Returned value: " + userRepoProxy.find());
    }

}

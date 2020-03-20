package com.ivk23.helloworld.newway.components;

import org.springframework.stereotype.Repository;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    public void populateCache() {
        System.out.println("Populating Cache");
    }

    public void clearCache(){
        System.out.println("Clearing Cache");
    }
}

package com.ivk23.helloworld.scopes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
@ComponentScan("com.ivk23.helloworld.scopes")
public class Config {

    // that's basically the same as if we had @Component and @Scope on the class
    @Bean
    @Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SimpleJavaBean getSimpleJavaBean() {
        return new SimpleJavaBean();
    }

}

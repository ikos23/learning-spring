package com.ivk23.helloworld.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ivk23.helloworld.autowiring")
public class Config {

    @Autowired
    private MessageSource messageSource;

    // This method will return instance of
    // org.springframework.context.support.DelegatingMessageSource
    // The interesting part is that even if the context doesn't
    // define its own MessageSource, there is this empty MessageSource,
    // so if we inject it somewhere (like in line 14 ^) we get the bean.
    @Bean(name = "myMessageSource")
    public MessageSource getMsgSource() {
        return this.messageSource;
    }
}

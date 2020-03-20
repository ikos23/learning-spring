package com.ivk23.helloworld.newway.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "com.ivk23.helloworld.newway")
@PropertySource("classpath:props1.properties")
public class SimpleConfig {

    @Value("${hello.world.prop}")
    private String property;

    @Bean(value = "simpleBean2", initMethod = "initMe")
    public SimpleBean sb2() {
        return new SimpleBean("sb2 field", property);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(initMethod = "populateCache")
    public AccountRepository accountRepository(){
        return new JdbcAccountRepository();
    }

}

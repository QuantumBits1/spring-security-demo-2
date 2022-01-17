package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//disable cross-origin-access policy
@Configuration
@Order(98)  //define ordering of annotated components or beans autowired beans of the same type based on their value
public class ServerSecurityConfigurationDev extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable();
    }
}

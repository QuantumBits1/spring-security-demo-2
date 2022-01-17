package com.example.demo;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="user")
public class UserProperties {
    public String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}

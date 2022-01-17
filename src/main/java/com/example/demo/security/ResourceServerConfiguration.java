package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


//Resource server
@Configuration
@EnableResourceServer  //enables a Spring Security filter that authenticates requests via incoming OAuth2 token
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public ResourceServerConfiguration() {
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("api");  //resource id, must match with client's access defined in previous class
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/signin**").permitAll()
                .antMatchers("/api/signin/**").permitAll()
                .antMatchers("/api/glee**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/users**").hasAuthority("ADMIN")
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated();
    }
}

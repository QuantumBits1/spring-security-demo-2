package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

//Authorization server
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.clientId:auth}")
    private String clientId;

    @Value("${jwt.client-secret:secret}")
    private String clientSecret;

    @Value("${jwt.authorizedGrantTypes:password, authorization_code, refresh_token}")
    private String[] authorizedGrantTypes;

    @Value("${jwt.accessTokenValiditySeconds:600}")
    private int accessTokenValiditySeconds;

    @Value("${jwt.refreshTokenValiditySeconds:2592000}")
    private int refreshTokenValiditySeconds;

    public OAuthConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                              UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;  //provide auth method
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;  //interface related to user's information
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients();
    }
    //client access
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)  //allowed grant types
                .scopes("read", "write")  //access scope
                .resourceIds("api");  //accessible resources
    }

    //auth endpoint
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);  //an interface to perform authentication (as resource owner)
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
}

package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * ClassName:
 * Function:
 * date: 2019年01月14日
 *
 * @author 许嘉阳
 */
@Configuration
@EnableResourceServer
public class OAuthResourceServer extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and()
                .requestMatchers().antMatchers("/api/**");
    }

//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
//        remoteTokenServices.setClientId("clientApp");
//        remoteTokenServices.setClientSecret("secret");
//        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//        return remoteTokenServices;
//    }
//
//    @Bean
//    public AccessTokenConverter accessTokenConverter() {
//        return new DefaultAccessTokenConverter();
//    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("mip");
    }
}

package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ClassName:
 * Function:
 * date: 2019年01月14日
 *
 * @author 许嘉阳
 */
//@Configuration
//@EnableResourceServer
//@Order(6)
public class OAuthResourceServer extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and()
//                .requestMatchers().antMatchers("/api/**");
//    }
}

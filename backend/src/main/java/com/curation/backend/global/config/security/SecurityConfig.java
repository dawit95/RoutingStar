package com.curation.backend.global.config.security;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(                        "/h2-console/**"
                ,"/favicon.ico", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**", "/webjars/springfox-swagger-ui/*.{js,css}", "/api/v1/**");
    }
}

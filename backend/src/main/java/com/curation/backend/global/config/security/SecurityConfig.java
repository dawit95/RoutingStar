package com.curation.backend.global.config.security;

import com.curation.backend.global.config.auth.CustomOAuth2UserService;
import com.curation.backend.global.filter.JwtAuthFilter;
import com.curation.backend.global.handler.CustomOAuth2SuccessHandler;
import com.curation.backend.global.token.service.TokenService;
import com.curation.backend.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.trace("http:",http.toString());
        http
                .cors()
                .and()
                //CSRF비활성화
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    //antMatchers의 url은 frontend와 함께 정리하여 변환.
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .antMatchers("/auth/**","/oauth2/**").permitAll()
                    .antMatchers("/token/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                .oauth2Login().loginPage("/token/expired")
                    .successHandler(customOAuth2SuccessHandler)
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(                        "/h2-console/**"
                ,"/favicon.ico", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**", "/webjars/springfox-swagger-ui/*.{js,css}", "/api/v1/**");
    }
}

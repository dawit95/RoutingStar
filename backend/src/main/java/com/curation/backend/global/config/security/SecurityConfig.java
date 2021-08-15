package com.curation.backend.global.config.security;

import com.curation.backend.global.config.auth.CustomOAuth2UserService;
import com.curation.backend.global.filter.JwtAuthFilter;
import com.curation.backend.global.handler.CustomOAuth2SuccessHandler;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.Role;
import com.curation.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .headers()
                .frameOptions()
                .disable()

                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**","/error","/favicon.ico").permitAll()
                //antMatchers의 url은 frontend와 함께 정리하여 변환.
                .antMatchers("/auth/**","/oauth2/**").permitAll()
                .antMatchers("/token/**","/api/v1/**").hasRole(Role.USER.name())

                .antMatchers("/api/guest/**","/api/user/**","/push/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .logout()
                .logoutSuccessUrl("/")
                //front-end CI/CD 구현시 변경될 uri
//                .logoutSuccessUrl("http://i5a309.p.ssafy.io:8080/home")

                .and()
                .oauth2Login()
                //front-end CI/CD 구현시 변경될 uri
                .loginPage("https://i5a309.p.ssafy.io/")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)

                .and()
                .successHandler(customOAuth2SuccessHandler);

        http.addFilterBefore(new JwtAuthFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico",
                        "/swagger-ui.html/**",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/webjars/springfox-swagger-ui/*.{js,css}");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

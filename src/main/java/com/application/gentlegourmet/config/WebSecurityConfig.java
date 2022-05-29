package com.application.gentlegourmet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig /*implements WebSecurityConfigurer*/ {

    /*@Override
    public void init(SecurityBuilder builder) throws Exception {

    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {

    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("customer").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/customer-signin")
                    .usernameParameter("customerId")
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .rememberMe()
                    .key("123456790_qwerasdfzxcv")
                    .tokenValiditySeconds(14 * 24 * 60 * 60);

        return http.build();
    }


}

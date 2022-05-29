package com.application.gentlegourmet.config;

import com.application.gentlegourmet.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public UserDetailsService userDetailsService() {
        return new CustomerUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable() //had to add to relieve 403 forbidden error (and also save time) ..
            .authorizeRequests()
                .antMatchers("/add-to-cart", "/checkout/**").authenticated()
                .anyRequest().permitAll()
            .and()
                .formLogin()
                    //remember.. home.html has the login-modal which shows a login popup but stays in home.html
                    .loginPage("/home")
                    //tells Spring Security to process the submitted credentials when the specified url is requested
                    .loginProcessingUrl("/customer-signin")
                    //tricky .. usernameParameter here means the name of html input has to be set as "username"
                    .usernameParameter("username")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/home?error=true")
            .and()
                .logout()
                    .invalidateHttpSession(true)
                    .invalidateHttpSession(true)
                    .logoutUrl("/signout")
                    .permitAll()
            .and()
                .rememberMe()
                    .key("123456790_qwerasdfzxcv")
                    .tokenValiditySeconds(14 * 24 * 60 * 60);

        return http.build();
    }

}

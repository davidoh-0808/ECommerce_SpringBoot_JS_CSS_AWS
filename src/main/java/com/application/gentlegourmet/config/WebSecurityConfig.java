package com.application.gentlegourmet.config;

import com.application.gentlegourmet.service.CustomerOAuth2UserService;
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


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerUserDetailsService();
    }

    // for Google social login : coming from CustomerOAuth2UserService and CustomerOAuth2User
    @Bean
    public CustomerOAuth2UserService customerOAuth2UserService() {
        return new CustomerOAuth2UserService();
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
                .oauth2Login()
                    .loginPage("/home")
                    .defaultSuccessUrl("/home", true)
                    //.loginProcessingUrl("/customer-signin-google")
                    .userInfoEndpoint()
                    .userService(customerOAuth2UserService()) //inject OAuth2 Service Bean
            .and()
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

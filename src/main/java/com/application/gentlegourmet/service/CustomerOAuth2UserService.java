package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.CustomerOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

    //a request the OAuth2UserService uses when initiating a request to the UserInfo Endpoint.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return new CustomerOAuth2User(oAuth2User);
    }
}

package com.demo.oauth2.client.config;

import com.demo.oauth2.client.registrations.CompositeOAuth2AccessTokenResponseClient;
import com.demo.oauth2.client.registrations.CompositeOAuth2UserService;
import com.demo.oauth2.client.registrations.qq.QQOAuth2AccessTokenResponseClient;
import com.demo.oauth2.client.registrations.qq.QQOAuth2UserService;
import com.demo.oauth2.client.registrations.qq.QQUserInfo;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String QQRegistrationId = "qq";
    public static final String WeChatRegistrationId = "wechat";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        http.oauth2Login()
                .redirectionEndpoint().baseUri("/register/social/*")
                .and()
                .tokenEndpoint().accessTokenResponseClient(this.accessTokenResponseClient())
                .and()
                .userInfoEndpoint()
                .customUserType(QQUserInfo.class, "qq")
                .userService(oauth2UserService());

    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        CompositeOAuth2AccessTokenResponseClient client = new CompositeOAuth2AccessTokenResponseClient();
        client.getOAuth2AccessTokenResponseClients().put(QQRegistrationId, new QQOAuth2AccessTokenResponseClient());
        return client;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        CompositeOAuth2UserService service = new CompositeOAuth2UserService();
        service.getUserServices().put(QQRegistrationId, new QQOAuth2UserService());
        return service;
    }
}

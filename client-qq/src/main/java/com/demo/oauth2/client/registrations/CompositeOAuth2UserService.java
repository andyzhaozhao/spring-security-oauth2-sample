package com.demo.oauth2.client.registrations;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CompositeOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private Map<String, OAuth2UserService> userServices;

    private static final String DefaultUserServiceKey = "default_key";

    public CompositeOAuth2UserService() {
        this.userServices = new HashMap();
        // DefaultOAuth2UserService是默认处理Oauth2协议获取用户逻辑的OAuth2UserService实现类
        // 将其预置到组合类CompositeOAuth2AccessTokenResponseClient中，从而默认支持GOOGLE，OKTA，GITHUB，FACEBOOK
        this.userServices.put(DefaultUserServiceKey, new DefaultOAuth2UserService());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        OAuth2UserService service = userServices.get(clientRegistration.getRegistrationId());
        if (service == null) {
            service = userServices.get(DefaultUserServiceKey);
        }

        return service.loadUser(userRequest);
    }

    public Map<String, OAuth2UserService> getUserServices() {
        return userServices;
    }
}

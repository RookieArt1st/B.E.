package com.example.demo.login.social.service;


import com.example.demo.login.social.dto.SessionUser;
import com.example.demo.login.social.dto.SocialDto;
import com.example.demo.login.social.dto.OAuthAttributes;
import com.example.demo.login.social.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

//        if(registrationId.equals("google")) {
//            attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        }

        SocialDto googleDto = saveOrUpdate(attributes);
        System.out.println(attributes.getAttributes());

        httpSession.setAttribute("googleDto", new SessionUser(googleDto));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(
                        googleDto.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }


    /**
     * 이미 존재하는 회원이라면 이름과 프로필이미지를 업데이트 해준다.
     * 처음 가입하는 회원이라면 user 테이블을 생성한다.
     **/
    private SocialDto saveOrUpdate(OAuthAttributes attributes) {
        SocialDto googleDto = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());

        return userRepository.save(googleDto);
    }


}
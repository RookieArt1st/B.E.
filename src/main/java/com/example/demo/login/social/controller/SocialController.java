package com.example.demo.login.social.controller;

import com.example.demo.response.SingleResponseData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/google")
    public SingleResponseData<OAuth2User> googleLogin(@AuthenticationPrincipal OAuth2User user){
        return SingleResponseData.of(user);
    }

    @GetMapping("/login/oauth2/code/kakao")
    public SingleResponseData<OAuth2User> kakaoLogin(@AuthenticationPrincipal OAuth2User user){
        return SingleResponseData.of(user);
    }




}

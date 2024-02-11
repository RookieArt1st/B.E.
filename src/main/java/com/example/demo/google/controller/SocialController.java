package com.example.demo.google.controller;

import com.example.demo.google.service.CustomOAuth2UserService;
import com.example.demo.response.SingleResponseData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SocialController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public SingleResponseData<OAuth2User> googleLogin(@AuthenticationPrincipal OAuth2User user){
        return SingleResponseData.of(user);
    }

}

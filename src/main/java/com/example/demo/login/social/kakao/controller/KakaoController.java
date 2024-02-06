package com.example.demo.login.social.kakao.controller;

import com.example.demo.login.common.MsgEntity;
import com.example.demo.login.social.kakao.dto.KakaoDto;
import com.example.demo.login.social.kakao.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<HttpStatus> callback(HttpServletRequest request) throws Exception {
        kakaoService.getKakaoInfo(request.getParameter("code"));

        return ResponseEntity.ok(HttpStatus.OK);
    }
}

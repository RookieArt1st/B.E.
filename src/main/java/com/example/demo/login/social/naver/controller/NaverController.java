package com.example.demo.login.social.naver.controller;

import com.example.demo.login.common.MsgEntity;
import com.example.demo.login.social.naver.dto.NaverDto;
import com.example.demo.login.social.naver.service.NaverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("naver")
public class NaverController {

    private final NaverService naverService;

    @GetMapping("/callback")
    public ResponseEntity<HttpStatus> callback(HttpServletRequest request) throws Exception {
        naverService.getNaverInfo(request.getParameter("code"));

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
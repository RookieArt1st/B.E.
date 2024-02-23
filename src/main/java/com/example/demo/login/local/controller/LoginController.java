package com.example.demo.login.local.controller;

import com.example.demo.login.local.record.LoginRequestRecord;
import com.example.demo.login.local.record.ResponseRecord;
import com.example.demo.login.local.service.JoinService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/main")
@RequiredArgsConstructor
public class LoginController {

    private final JoinService joinService;

    @PostMapping("/login")
    public ResponseEntity<ResponseRecord> login(@RequestBody LoginRequestRecord loginRequest, HttpSession session) {
        ResponseEntity<ResponseRecord> responseEntity = joinService.match(loginRequest);
        return joinService.handleLoginResponse(responseEntity, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션 만료시키기
        session.invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}

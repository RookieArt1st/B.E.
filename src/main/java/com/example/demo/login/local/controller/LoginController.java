package com.example.demo.login.local.controller;

import com.example.demo.login.local.dto.LoginRequestDto;
import com.example.demo.login.local.dto.ResponseDto;
import com.example.demo.login.local.service.JoinService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JoinService joinService;

    @PostMapping("/check")
    public ResponseEntity<ResponseDto> check(@RequestBody LoginRequestDto dto, HttpSession session) {
        ResponseEntity<ResponseDto> responseEntity = joinService.find(dto);

        // 성공
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("로그인에 성공했습니다.");

            // 세션 정보에 로그인 저장
            session.setAttribute("loginUser", responseEntity.getBody());

            // 세션 정보 체크
            ResponseDto loginUser = (ResponseDto) session.getAttribute("loginUser");
            log.info("세션에 저장된 사용자 정보: {}", loginUser.getUsername());

            return ResponseEntity.ok(responseEntity.getBody());
        }

        log.info("로그인에 실패했습니다.");
        // 실패
        return ResponseEntity.badRequest().body(responseEntity.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션 만료시키기
        session.invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}

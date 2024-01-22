package com.example.demo.login.local.controller;

import com.example.demo.login.local.dto.LoginRequestDto;
import com.example.demo.login.local.dto.ResponseDto;
import com.example.demo.login.local.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JoinService joinService;

    @PostMapping("/check")
    public ResponseEntity<ResponseDto> check(@RequestBody LoginRequestDto dto) {
        ResponseEntity<ResponseDto> responseEntity = joinService.find(dto);

        // 성공
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("로그인에 성공했습니다.");
            return ResponseEntity.ok(responseEntity.getBody());
        }

        log.info("로그인에 실패했습니다.");
        // 실패
        return ResponseEntity.badRequest().body(responseEntity.getBody());
    }
}

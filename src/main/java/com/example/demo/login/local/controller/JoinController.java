package com.example.demo.login.local.controller;

import com.example.demo.login.local.dto.JoinRequestDto;
import com.example.demo.login.local.dto.ResponseDto;
import com.example.demo.login.local.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("login/join")
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/create")     // 회원가
    public ResponseEntity<ResponseDto> create(@RequestBody JoinRequestDto dto) {
        ResponseEntity<ResponseDto> responseEntity = joinService.save(dto);

        // 성공이면 상태 코드를 OK로 변경
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("회원가입에 성공했습니다.");
            return ResponseEntity.ok(responseEntity.getBody());
        }

        log.info("회원가입에 실패했습니다.");
        // 실패일 경우 그대로 반환
        return responseEntity;
    }

    @GetMapping("/checkId")   // id 체크
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        boolean isUnique = joinService.isUniqueNickName(username);
        String message = isUnique ? "사용 가능한 ID입니다." : "이미 사용 중인 ID입니다.";

        HttpStatus status = isUnique ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, status);
    }
}
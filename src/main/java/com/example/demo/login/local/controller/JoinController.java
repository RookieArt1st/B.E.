package com.example.demo.login.local.controller;

import com.example.demo.login.local.dto.JoinRequestDto;
import com.example.demo.login.local.dto.ResponseDto;
import com.example.demo.login.local.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/")     // 회원가
    public ResponseEntity<ResponseDto> create(@RequestBody JoinRequestDto dto) {
        ResponseEntity<ResponseDto> responseEntity = joinService.join(dto);
        return joinService.handleJoinResponse(responseEntity);
    }

    @GetMapping("/idCheck")   // id 체크
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        boolean isUnique = joinService.isUniqueUsername(username);
        String message = isUnique ? "사용 가능한 ID입니다." : "이미 사용 중인 ID입니다.";

        HttpStatus status = isUnique ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, status);
    }
}
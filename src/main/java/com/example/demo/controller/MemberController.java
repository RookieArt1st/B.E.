package com.example.demo.controller;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<MemberResponseDto> create(@RequestBody MemberRequestDto dto) {
        ResponseEntity<MemberResponseDto> responseEntity = memberService.saveUser(dto);

        // 성공이면 상태 코드를 OK로 변경
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        }

        // 실패일 경우 그대로 반환
        return responseEntity;
    }

    @GetMapping("/checkNickName")
    public ResponseEntity<String> checkUsername(@RequestParam String nickname) {
        boolean isUnique = memberService.isUniqueNickName(nickname);
        String message = isUnique ? "사용 가능한 ID입니다." : "이미 사용 중인 ID입니다.";

        HttpStatus status = isUnique ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, status);
    }

}
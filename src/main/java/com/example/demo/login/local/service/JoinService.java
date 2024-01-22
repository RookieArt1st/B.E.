package com.example.demo.login.local.service;

import com.example.demo.login.local.dto.JoinRequestDto;
import com.example.demo.login.local.dto.LoginRequestDto;
import com.example.demo.login.local.dto.ResponseDto;
import com.example.demo.login.local.entity.Member;
import com.example.demo.login.local.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public ResponseEntity<ResponseDto> join(JoinRequestDto dto) {
        Member savedMember = memberRepository.save(dto.toMemberEntity(passwordEncoder.encode(dto.getPw())));

        ResponseDto responseDto = ResponseDto.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMember.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @Transactional(readOnly = true)
    public boolean isUniqueNickName(String username) {
        Optional<Member> existingMember = memberRepository.findByUsername(username);
        return existingMember.isEmpty();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> find(LoginRequestDto dto) {
        try {
            Member member = dto.toMemberEntity();   // 로그인할 Id, pw 넣을 때
            Optional<Member> user = memberRepository.findByUsername(member.getUsername());      // 해당 id가 존재하는지 여부

            if (user.isPresent() && passwordEncoder.matches(dto.getPw(), user.get().getPassword())) {
                member = user.get();

                ResponseDto responseDto = ResponseDto.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .build();

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(member.getId())
                        .toUri();

                return ResponseEntity.created(location).body(responseDto);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(0L, "User not found"));

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(0L, "Error occurred"));
        }
    }

}

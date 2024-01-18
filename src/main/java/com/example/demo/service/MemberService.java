package com.example.demo.service;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public ResponseEntity<MemberResponseDto> saveUser(MemberRequestDto dto) {
        Member savedMember = memberRepository.save(dto.toUserEntity());

        MemberResponseDto responseDto = MemberResponseDto.builder()
                .id(savedMember.getId())
                .username(savedMember.getNickname())
                .build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMember.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    public boolean isUniqueNickName(String nickname) {
        Optional<Member> existingMember = memberRepository.findByNickname(nickname);
        return existingMember.isEmpty();
    }
}

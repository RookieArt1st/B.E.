package com.example.demo.login.local.service;

import com.example.demo.login.local.entity.Member;
import com.example.demo.login.local.record.JoinRequestRecord;
import com.example.demo.login.local.record.LoginRequestRecord;
import com.example.demo.login.local.record.ResponseRecord;
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
    public ResponseEntity<ResponseRecord> join(JoinRequestRecord joinRequest) {
        if (!isUniqueUsername(joinRequest.username())) {
            log.info("이미 존재하는 id가 있습니다. 중복 체크를 확인해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseRecord(0L, "Username already exists", "join fail"));
        }
        Member member = memberRepository.save(joinRequest.toMemberEntity(passwordEncoder.encode(joinRequest.pw())));

        ResponseRecord responseRecord = new ResponseRecord(
                member.getId(),
                member.getUsername(),
                "join success"
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseRecord);
    }

    @Transactional(readOnly = true)
    public boolean isUniqueUsername(String username) {
        return memberRepository.findByUsername(username).isEmpty();
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ResponseRecord> find(LoginRequestRecord loginRequest) {
        try {
            Member member = loginRequest.toMemberEntity();   // 로그인할 Id, pw 넣을 때
            Optional<Member> user = memberRepository.findByUsername(member.getUsername());      // 해당 id가 존재하는지 여부

            if (user.isPresent() && passwordEncoder.matches(loginRequest.pw(), user.get().getPassword())) {
                member = user.get();

                ResponseRecord responseRecord = new ResponseRecord(
                        member.getId(),
                        member.getUsername(),
                        "login success"
                );

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(member.getId())
                        .toUri();

                return ResponseEntity.created(location).body(responseRecord);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseRecord(0L, "User not found","Absent User"));

        } catch (Exception e) {
            // 예외 처리
            log.error("id 혹은 pw 확인해주세요.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseRecord(0L, "Error occurred", "Error"));
        }
    }

    public ResponseEntity<ResponseRecord> handleJoinResponse(ResponseEntity<ResponseRecord> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("회원가입에 성공했습니다.");
            return ResponseEntity.ok(responseEntity.getBody());
        }
        log.info("회원가입에 실패했습니다.");
        return responseEntity;
    }
}

package com.example.demo.login.local.dto;

import com.example.demo.login.local.entity.Member;
import com.example.demo.login.local.entity.MemberType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class JoinRequestDto {
    private String pw;
    private String username;
    private String address;
    private String phone_number;
    private String card_number;

    public JoinRequestDto(String pw, String username, String address, String phone_number, String card_number) {
        this.pw = pw;
        this.username = username;
        this.address = address;
        this.phone_number = phone_number;
        this.card_number = card_number;
    }

    public Member toMemberEntity(String encode) {
        return Member.builder()
                .password(encode)
                .username(this.username)
                .createdAt(LocalDateTime.now()) // 현재 시간으로 초기화
                .role(MemberType.USER)
                .isArtist(false)
                .image(null)
                .artPieceCount(0)
                .address(this.address)
                .phoneNumber(this.phone_number)
                .cardNumber(this.card_number)
                .salesArtCount(0)
                .build();
    }
}

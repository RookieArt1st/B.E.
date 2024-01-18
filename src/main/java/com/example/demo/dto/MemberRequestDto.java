package com.example.demo.dto;

import com.example.demo.entity.Member;
import lombok.*;

@Getter
@Builder
public class MemberRequestDto {
    private String pw;
    private String username;
    private String address;
    private String phone_number;
    private String card_number;

    public MemberRequestDto(String pw, String username, String address, String phone_number, String card_number) {
        this.pw = pw;
        this.username = username;
        this.address = address;
        this.phone_number = phone_number;
        this.card_number = card_number;
    }

    public Member toUserEntity() {
        // 순서를 맞춰서 생성자 호출
        return new Member(
                null, // id는 자동으로 생성되므로 null로 초기화
                this.pw,
                this.username,
                null, // created_at은 자동으로 생성되므로 null로 초기화
                null, // role은 기본값으로 초기화
                false, // isArtist는 기본값으로 초기화
                null, // image는 기본값으로 초기화
                0, // art_piece_count는 기본값으로 초기화
                this.address,
                this.phone_number,
                this.card_number,
                0 // sales_art_count는 기본값으로 초기화
        );
    }
}

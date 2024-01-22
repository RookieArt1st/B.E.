package com.example.demo.login.local.dto;

import com.example.demo.login.local.entity.Member;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String username;
    private String pw;
    public LoginRequestDto(String username, String pw) {
        this.username = username;
        this.pw = pw;
    }

    public Member toMemberEntity() {
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
                null,
                null,
                null,
                0 // sales_art_count는 기본값으로 초기화
        );
    }
}
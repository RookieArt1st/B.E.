package com.example.demo.login.social.naver.dto;

import com.example.demo.login.social.naver.entity.NaverMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverDto {
    private String name;
    private String email;
    private String profile_image;
    private String mobile;
}

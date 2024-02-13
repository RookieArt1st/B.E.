package com.example.demo.login.social.kakao.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoDto {
    private long kakao_id;
    private String email;
    private String profile_image_url;
    private String nickname;
}

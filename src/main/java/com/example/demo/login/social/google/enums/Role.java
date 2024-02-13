package com.example.demo.login.social.google.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    BUYER("ROLE_BUYER","구매자"),
    ARTIST("ROLE_ARTIST","아티스트");

    private final String key;
    private final String title;
}

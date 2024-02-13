package com.example.demo.login.local.record;

import com.example.demo.login.local.entity.Member;

import lombok.NonNull;

public record LoginRequestRecord(
        @NonNull String username,
        @NonNull String pw
) {
    public Member toMemberEntity() {
        return new Member(
                null,
                this.pw,
                this.username,
                null,
                null,
                false,
                null,
                0,
                null,
                null,
                null,
                0
        );
    }
}

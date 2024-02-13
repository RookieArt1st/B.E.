package com.example.demo.login.local.record;

import com.example.demo.login.local.entity.Member;
import com.example.demo.login.local.entity.MemberType;
import lombok.NonNull;
import java.time.LocalDateTime;

public record JoinRequestRecord(
        @NonNull String pw,
        @NonNull String username,
        @NonNull String address,
        @NonNull String phone_number,
        @NonNull String card_number
) {
    public Member toMemberEntity(String encode) {
        return new Member(
                null,
                encode,
                this.username,
                LocalDateTime.now(),
                MemberType.USER,
                false,
                null,
                0,
                this.address,
                this.phone_number,
                this.card_number,
                0
        );
    }
}

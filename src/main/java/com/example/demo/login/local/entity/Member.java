package com.example.demo.login.local.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password; // "pw"를 "password"로 변경
    private String username;
    private LocalDateTime createdAt = LocalDateTime.now(); // 현재 시간으로 초기화
    private MemberType role;
    private boolean isArtist;
    private String image;
    private Integer artPieceCount; // 카멜 케이스 사용 및 "art_piece_count"를 "artPieceCount"로 변경
    private String address;
    private String phoneNumber; // 카멜 케이스 사용 및 "phone_number"를 "phoneNumber"로 변경
    private String cardNumber;
    private Integer salesArtCount; // 카멜 케이스 사용 및 "sales_art_count"를 "salesArtCount"로 변경
    // 필요한 경우 생성자 로직 추가
}

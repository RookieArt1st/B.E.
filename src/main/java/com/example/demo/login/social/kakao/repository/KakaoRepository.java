package com.example.demo.login.social.kakao.repository;

import com.example.demo.login.social.kakao.entity.KakaoMember;
import com.example.demo.login.social.naver.entity.NaverMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoRepository extends JpaRepository<KakaoMember, Long> {
}

package com.example.demo.login.social.naver.repository;

import com.example.demo.login.social.naver.entity.NaverMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaverRepository extends JpaRepository<NaverMember, Long>{
}
package com.example.demo.login.social.repository;

import com.example.demo.login.social.dto.SocialDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SocialDto, Long> {
    Optional<SocialDto> findByEmail(String email);
}

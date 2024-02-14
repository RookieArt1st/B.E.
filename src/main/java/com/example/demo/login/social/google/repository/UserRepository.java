package com.example.demo.login.social.google.repository;

import com.example.demo.login.social.google.dto.GoogleDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<GoogleDto, Long> {
    Optional<GoogleDto> findByEmail(String email);
}

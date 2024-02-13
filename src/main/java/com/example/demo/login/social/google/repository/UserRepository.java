package com.example.demo.login.social.google.repository;

import com.example.demo.login.social.google.dto.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

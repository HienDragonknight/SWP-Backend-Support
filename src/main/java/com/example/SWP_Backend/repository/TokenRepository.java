package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository thao tác với bảng VerificationToken (dùng cho xác thực email ĐĂNG KÝ hoặc OTP QUÊN MẬT KHẨU).
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    Optional<Token> findByEmailAndType(String email, String type);
}

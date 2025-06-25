package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    // Đăng nhập
    User findByUsername(String username);
    User findByEmail(String email);

    // Phân quyền
    List<User> findByRole(String role);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // --- BỎ hoặc COMMENT các hàm dưới vì entity User không còn coachId primitive ---
    // List<User> findByCoachIdIsNotNull();
    // List<User> findByCoachId(Long coachId);

    // --- CHỈ DÙNG các hàm mapping với Coach object ---
    List<User> findByCoach(Coach coach);           // Lấy user theo coach object
    List<User> findByCoachIsNotNull();             // Lấy user có coach (object Coach != null)

    // Linh hoạt đăng nhập (username hoặc email)
    User findByUsernameOrEmail(String username, String email);
}
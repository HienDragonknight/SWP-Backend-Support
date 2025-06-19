package com.example.SWP_Backend.repository;


import com.example.SWP_Backend.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    boolean existsByUserUserId(Long userId); // nếu muốn check coach từ userId

    Coach findByUserUserId(Long userId); // nếu cần tìm coach dựa theo user
}


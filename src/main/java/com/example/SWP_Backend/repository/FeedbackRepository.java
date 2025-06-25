package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Có thể bổ sung findByTargetType, findByUser, v.v.
}

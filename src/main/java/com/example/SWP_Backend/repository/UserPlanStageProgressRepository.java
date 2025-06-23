package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.UserPlanStageProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPlanStageProgressRepository extends JpaRepository<UserPlanStageProgress, Long> {
    List<UserPlanStageProgress> findByUserIdAndCompletedTrue(Long userId);
    Optional<UserPlanStageProgress> findByUserIdAndSequenceOrder(Long userId, Integer sequenceOrder);
    List<UserPlanStageProgress> findByUserId(Integer userId);

}

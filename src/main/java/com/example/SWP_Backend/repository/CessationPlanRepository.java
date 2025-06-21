package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.CessationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CessationPlanRepository extends JpaRepository<CessationPlan, Long> {
    List<CessationPlan> findByUserUserIdAndIsActive(Long userId, boolean isActive);
}


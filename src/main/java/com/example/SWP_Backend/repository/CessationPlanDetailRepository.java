package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.CessationPlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CessationPlanDetailRepository extends JpaRepository<CessationPlanDetail, Long> {
    List<CessationPlanDetail> findByPlan_PlanID(Long planId);
}

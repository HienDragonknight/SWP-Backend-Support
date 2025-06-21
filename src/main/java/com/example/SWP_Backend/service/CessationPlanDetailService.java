package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.CessationPlanDetail;

import java.util.List;

public interface CessationPlanDetailService {
    List<CessationPlanDetail> saveAll(List<CessationPlanDetail> details);
    List<CessationPlanDetail> getDetailsByPlanId(Long planId);
}

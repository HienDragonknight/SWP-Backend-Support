package com.example.SWP_Backend.service;

import com.example.SWP_Backend.DTO.CessationPlanRequest;
import com.example.SWP_Backend.entity.CessationPlan;

import java.util.List;

public interface CessationPlanService {
    CessationPlan addPlan(CessationPlanRequest req);
    List<CessationPlan> getActivePlans(Long userId);
}


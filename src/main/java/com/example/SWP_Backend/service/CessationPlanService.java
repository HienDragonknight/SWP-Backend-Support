package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.CessationPlanRequest;
import com.example.SWP_Backend.dto.CessationPlanUpdateRequest;
import com.example.SWP_Backend.entity.CessationPlan;

import java.util.List;

public interface CessationPlanService {
    CessationPlan addPlan(CessationPlanRequest req);
    List<CessationPlan> getActivePlans(Long userId);
    CessationPlan updatePlan(Long planId, CessationPlanUpdateRequest req);

}


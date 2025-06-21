package com.example.SWP_Backend.service;


import com.example.SWP_Backend.entity.CessationPlanDetail;
import com.example.SWP_Backend.repository.CessationPlanDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CessationPlanDetailServiceImpl implements CessationPlanDetailService {

    private final CessationPlanDetailRepository repository;

    public CessationPlanDetailServiceImpl(CessationPlanDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CessationPlanDetail> saveAll(List<CessationPlanDetail> details) {
        return repository.saveAll(details);
    }

    @Override
    public List<CessationPlanDetail> getDetailsByPlanId(Long planId) {
        return repository.findByPlan_PlanID(planId);
    }
}

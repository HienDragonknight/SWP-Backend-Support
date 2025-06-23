package com.example.SWP_Backend.service;

import com.example.SWP_Backend.DTO.CessationPlanRequest;
import com.example.SWP_Backend.DTO.CessationPlanUpdateRequest;
import com.example.SWP_Backend.entity.CessationPlan;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.CessationPlanRepository;
import com.example.SWP_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CessationPlanServiceImpl implements CessationPlanService {
    private final CessationPlanRepository planRepository;
    private final UserRepository userRepository;

    @Autowired
    public CessationPlanServiceImpl(CessationPlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CessationPlan addPlan(CessationPlanRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CessationPlan plan = new CessationPlan();
        plan.setUser(user);
        plan.setReasonToQuit(req.getReasonToQuit());
        plan.setStartDate(req.getStartDate());
        plan.setTargetQuitDate(req.getTargetQuitDate());
        plan.setCigarettesPerDay(req.getCigarettesPerDay());
        plan.setSmokingFrequency(req.getSmokingFrequency());
        plan.setCostPerPack(req.getCostPerPack());
        plan.setNotes(req.getNotes());
        plan.setCustomDetails(req.getCustomDetails());
        plan.setActive(true);
        return planRepository.save(plan);
    }
    @Override
    public CessationPlan updatePlan(Long planId, CessationPlanUpdateRequest req) {
        CessationPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("CessationPlan not found"));

        // Cập nhật từng trường
        if (req.getReasonToQuit() != null) plan.setReasonToQuit(req.getReasonToQuit());
        if (req.getStartDate() != null) plan.setStartDate(req.getStartDate());
        if (req.getTargetQuitDate() != null) plan.setTargetQuitDate(req.getTargetQuitDate());
        if (req.getCigarettesPerDay() != null) plan.setCigarettesPerDay(req.getCigarettesPerDay());
        if (req.getSmokingFrequency() != null) plan.setSmokingFrequency(req.getSmokingFrequency());
        if (req.getCostPerPack() != null) plan.setCostPerPack(req.getCostPerPack());
        if (req.getNotes() != null) plan.setNotes(req.getNotes());
        if (req.getCustomDetails() != null) plan.setCustomDetails(req.getCustomDetails());
        if (req.getIsActive() != null) plan.setActive(req.getIsActive());

        return planRepository.save(plan);
    }

    @Override
    public List<CessationPlan> getActivePlans(Long userId) {
        return planRepository.findByUserUserIdAndIsActive(userId, true);
    }
}

package com.example.SWP_Backend.service;

import com.example.SWP_Backend.DTO.CessationPlanRequest;
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
    public List<CessationPlan> getActivePlans(Long userId) {
        return planRepository.findByUserUserIdAndIsActive(userId, true);
    }
}

package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.service.PlanStageService;
import com.example.SWP_Backend.service.UserPlanStageProgressService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user-plan-stage-progress")
public class UserPlanStageProgressController {

    private final UserPlanStageProgressService progressService;
    private final PlanStageService planStageService;

    public UserPlanStageProgressController(UserPlanStageProgressService progressService,
                                           PlanStageService planStageService) {
        this.progressService = progressService;
        this.planStageService = planStageService;
    }

    /**
     * Tick or untick a stage as completed.
     */
    @PostMapping("/tick")
    public String tickStage(
            @RequestParam Long userId,
            @RequestParam Integer sequenceOrder,
            @RequestParam boolean completed) {
        progressService.tickStage(userId, sequenceOrder, completed);
        return completed ? "Stage completed" : "Stage uncompleted";
    }

    /**
     * Get percentage of plan completion.
     */
    @GetMapping("/progress")
    public double getUserProgress(
            @RequestParam Long userId,
            @RequestParam String mucDoKeHoach,
            @RequestParam int soNgay) throws IOException {
        // Load total stages
        int totalStages = planStageService.loadDaysForUser(
                "src/main/resources/ke_hoach_cai_thuoc_chi_tiet.xlsx",
                mucDoKeHoach,
                soNgay
        ).size();
        return progressService.getUserProgressPercentage(userId, totalStages);
    }
}

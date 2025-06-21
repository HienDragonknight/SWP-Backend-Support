package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.entity.PlanStage;
import com.example.SWP_Backend.service.PlanStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stages")
public class PlanStageController {

    private final PlanStageService planStageService;

    @Autowired

    public PlanStageController(PlanStageService planStageService) {
        this.planStageService = planStageService;
    }
    @GetMapping("/generate")
    public List<PlanStage> generateStages(
            @RequestParam String mucDoKeHoach,
            @RequestParam int soNgay) throws IOException {
        return planStageService.loadStagesForUser(
                "src/main/resources/ke_hoach_cai_thuoc_chi_tiet.xlsx",
                mucDoKeHoach,
                soNgay
        );
    }
}

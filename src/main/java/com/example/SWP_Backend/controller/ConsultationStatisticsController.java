package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.ConsultationStatsDTO;
import com.example.SWP_Backend.dto.MonthlyConsultationDTO;
import com.example.SWP_Backend.service.ConsultationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/consultations")
public class ConsultationStatisticsController {

    @Autowired
    private ConsultationStatisticsService statisticsService;

    @GetMapping("/summary/{coachId}")
    public ResponseEntity<ConsultationStatsDTO> getSummary(@PathVariable Long coachId) {
        return ResponseEntity.ok(statisticsService.getStatisticsByCoachId(coachId));
    }

    @GetMapping("/monthly/{coachId}")
    public ResponseEntity<List<MonthlyConsultationDTO>> getMonthly(@PathVariable Long coachId) {
        return ResponseEntity.ok(statisticsService.getMonthlyStatistics(coachId));
    }
}

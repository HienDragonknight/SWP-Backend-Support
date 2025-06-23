package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.DTO.ProgressReportDTO;
import com.example.SWP_Backend.service.ProgressReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressReportController {

    private final ProgressReportService progressReportService;

    public ProgressReportController(ProgressReportService progressReportService) {
        this.progressReportService = progressReportService;
    }

    @GetMapping("/{userId}")
    public ProgressReportDTO getUserProgress(@PathVariable Long userId) {
        return progressReportService.getProgress(userId);
    }
}

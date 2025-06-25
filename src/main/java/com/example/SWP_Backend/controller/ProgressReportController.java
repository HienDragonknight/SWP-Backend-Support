package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.ProgressReportDTO;
import com.example.SWP_Backend.service.ProgressReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

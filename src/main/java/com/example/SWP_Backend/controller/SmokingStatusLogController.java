package com.example.SWP_Backend.controller;


import com.example.SWP_Backend.DTO.SmokingStatusLogRequest;
import com.example.SWP_Backend.entity.SmokingStatusLog;
import com.example.SWP_Backend.service.SmokingStatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/smoking-logs")
public class SmokingStatusLogController {
    private final SmokingStatusLogService logService;

    @Autowired
    public SmokingStatusLogController(SmokingStatusLogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<SmokingStatusLog> addLog(@RequestBody SmokingStatusLogRequest req) {
        return ResponseEntity.ok(logService.addLog(req));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SmokingStatusLog>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(logService.getByUser(userId));
    }
}

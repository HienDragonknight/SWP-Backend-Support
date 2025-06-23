package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.entity.HabitLog;
import com.example.SWP_Backend.service.HabitLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/habit-logs")
public class HabitLogController {
    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    @PostMapping
    public HabitLog logHabit(@RequestBody HabitLog habitLog) {
        return habitLogService.logHabit(habitLog);
    }

    @GetMapping("/{userId}")
    public HabitLog getHabitLogForUser(@PathVariable Long userId) {
        return habitLogService.getHabitLog(userId, LocalDate.now());
    }
}

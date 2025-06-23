package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.HabitLog;
import com.example.SWP_Backend.repository.HabitLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class HabitLogService {
    private final HabitLogRepository habitLogRepository;

    public HabitLogService(HabitLogRepository habitLogRepository) {
        this.habitLogRepository = habitLogRepository;
    }

    public HabitLog logHabit(HabitLog habitLog) {
        // Gán logDate = hôm nay
        habitLog.setLogDate(LocalDate.now());

        Optional<HabitLog> existing = habitLogRepository.findByUserIdAndLogDate(habitLog.getUserId(), habitLog.getLogDate());
        if (existing.isPresent()) {
            HabitLog existingLog = existing.get();
            existingLog.setSmokedToday(habitLog.getSmokedToday());
            existingLog.setCigarettesSmoked(habitLog.getCigarettesSmoked());
            existingLog.setCravingsLevel(habitLog.getCravingsLevel());
            existingLog.setMood(habitLog.getMood());
            existingLog.setNotes(habitLog.getNotes());
            existingLog.setMoneySaved(habitLog.getMoneySaved());
            return habitLogRepository.save(existingLog);
        } else {
            return habitLogRepository.save(habitLog);
        }
    }

    public HabitLog getHabitLog(Long userId, LocalDate logDate) {
        return habitLogRepository.findByUserIdAndLogDate(userId, logDate).orElse(null);
    }
}

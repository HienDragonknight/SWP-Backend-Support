package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    Optional<HabitLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);
    List<HabitLog> findByUserIdAndLogDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}

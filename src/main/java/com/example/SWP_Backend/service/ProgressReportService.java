package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.ProgressReportDTO;
import com.example.SWP_Backend.entity.CessationPlan;
import com.example.SWP_Backend.entity.HabitLog;
import com.example.SWP_Backend.repository.CessationPlanRepository;
import com.example.SWP_Backend.repository.HabitLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProgressReportService {

    private final CessationPlanRepository cessationPlanRepository;
    private final HabitLogRepository habitLogRepository;

    public ProgressReportService(
            CessationPlanRepository cessationPlanRepository,
            HabitLogRepository habitLogRepository
    ) {
        this.cessationPlanRepository = cessationPlanRepository;
        this.habitLogRepository = habitLogRepository;
    }

    public ProgressReportDTO getProgress(Long userId) {
        // Lấy plan đang active
        CessationPlan plan = cessationPlanRepository
                .findFirstByUserUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User chưa có plan active"));

        LocalDate startDate = plan.getStartDate();
        LocalDate endDate = LocalDate.now();

        // Lấy danh sách habit logs trong khoảng thời gian
        List<HabitLog> habitLogs = habitLogRepository
                .findByUserIdAndLogDateBetween(userId, startDate, endDate);

        // Đếm số ngày không hút thuốc
        long daysQuit = habitLogs.stream()
                .filter(log -> log.getSmokedToday() == null || log.getSmokedToday() == false)
                .count();

        // Tính tiền tiết kiệm mỗi ngày
        BigDecimal cigarettesPerDay = BigDecimal.valueOf(plan.getCigarettesPerDay());
        BigDecimal costPerPack = plan.getCostPerPack();
        BigDecimal onePackCigarettes = BigDecimal.valueOf(20); // 1 gói 20 điếu

        BigDecimal dailyCost = cigarettesPerDay
                .divide(onePackCigarettes, 2, RoundingMode.HALF_UP)
                .multiply(costPerPack);

        BigDecimal moneySaved = dailyCost.multiply(BigDecimal.valueOf(daysQuit));

        return new ProgressReportDTO(daysQuit, moneySaved);
    }
}

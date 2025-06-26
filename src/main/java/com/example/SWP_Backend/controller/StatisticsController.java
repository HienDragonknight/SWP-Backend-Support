package com.example.SWP_Backend.controller;


import com.example.SWP_Backend.dto.MonthlyRevenueDTO;
import com.example.SWP_Backend.dto.MonthlyUserDTO;
import com.example.SWP_Backend.entity.MembershipPackage;
import com.example.SWP_Backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final ReportService reportService;

    @GetMapping("/revenue/total")
    public BigDecimal totalRevenue() {
        return reportService.getTotalRevenue();
    }

    @GetMapping("/revenue/subscribers")
    public long totalSubscribers() {
        return reportService.getTotalSubscribers();
    }

    @GetMapping("/revenue/average-per-member")
    public BigDecimal averageRevenuePerMember() {
        return reportService.getAverageRevenuePerMember();
    }

    @GetMapping("/revenue/most-popular-package")
    public MembershipPackage mostPopularPackage() {
        return reportService.getMostPopularPackage();
    }

    @GetMapping("/revenue/monthly")
    public List<MonthlyRevenueDTO> revenueByMonth() {
        return reportService.getMonthlyRevenue();
    }

    @GetMapping("/users/monthly")
    public List<MonthlyUserDTO> userCountsByMonth() {
        return reportService.getMonthlyUserCounts();
    }
}


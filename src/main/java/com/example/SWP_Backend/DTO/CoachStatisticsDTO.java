package com.example.SWP_Backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachStatisticsDTO {
    private int totalMembers;
    private int totalCompletedConsultations;
    private double successRate;
    private double averageDurationDays;
    // thời gian trung bình giữa lần đầu và lần cuối


    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public int getTotalCompletedConsultations() {
        return totalCompletedConsultations;
    }

    public void setTotalCompletedConsultations(int totalCompletedConsultations) {
        this.totalCompletedConsultations = totalCompletedConsultations;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public double getAverageDurationDays() {
        return averageDurationDays;
    }

    public void setAverageDurationDays(double averageDurationDays) {
        this.averageDurationDays = averageDurationDays;
    }
}

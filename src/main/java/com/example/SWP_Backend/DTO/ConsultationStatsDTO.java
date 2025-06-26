package com.example.SWP_Backend.dto;


public class ConsultationStatsDTO {
    private Long totalMembers;
    private Long completedSessions;
    private Long totalSessions;
    private double completionRate; // %
    private long activeDays; // Số ngày kể từ buổi đầu

    public ConsultationStatsDTO(Long totalMembers, Long completedSessions, Long totalSessions, double completionRate, long activeDays) {
        this.totalMembers = totalMembers;
        this.completedSessions = completedSessions;
        this.totalSessions = totalSessions;
        this.completionRate = completionRate;
        this.activeDays = activeDays;
    }

    public Long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(Long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public Long getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Long completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Long getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Long totalSessions) {
        this.totalSessions = totalSessions;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public long getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(long activeDays) {
        this.activeDays = activeDays;
    }
}


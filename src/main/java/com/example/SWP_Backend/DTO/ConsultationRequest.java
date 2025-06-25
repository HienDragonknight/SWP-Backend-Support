package com.example.SWP_Backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationRequest {
    private Long userId;
    private Long coachId;
    private LocalDateTime scheduledTime; // thời gian hẹn tư vấn
    private String notes;

    public ConsultationRequest(Long userId, Long coachId, LocalDateTime scheduledTime, String notes) {
        this.userId = userId;
        this.coachId = coachId;
        this.scheduledTime = scheduledTime;
        this.notes = notes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // ghi chú (nếu có)
}

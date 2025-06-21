package com.example.SWP_Backend.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CessationPlanRequest {
    private Long userId;
    private String reasonToQuit;
    private LocalDate startDate;
    private LocalDate targetQuitDate;
    private Integer cigarettesPerDay;
    private String smokingFrequency;
    private BigDecimal costPerPack;
    private String notes;
    private String customDetails;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReasonToQuit() {
        return reasonToQuit;
    }

    public void setReasonToQuit(String reasonToQuit) {
        this.reasonToQuit = reasonToQuit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetQuitDate() {
        return targetQuitDate;
    }

    public void setTargetQuitDate(LocalDate targetQuitDate) {
        this.targetQuitDate = targetQuitDate;
    }

    public Integer getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(Integer cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public String getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(String smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public BigDecimal getCostPerPack() {
        return costPerPack;
    }

    public void setCostPerPack(BigDecimal costPerPack) {
        this.costPerPack = costPerPack;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCustomDetails() {
        return customDetails;
    }

    public void setCustomDetails(String customDetails) {
        this.customDetails = customDetails;
    }
}


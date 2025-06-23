package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "DailyProgress")
@Getter @Setter @NoArgsConstructor
public class DailyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Long progressId;

    @Column(name = "UserID")
    private Long userId;

    @Column(name = "PlanID")
    private Long planId;

    @Column(name = "LogDate")
    private LocalDate logDate;

    @Column(name = "SmokedToday")
    private Boolean smokedToday;

    @Column(name = "CigarettesSmoked")
    private Integer cigarettesSmoked;

    @Column(name = "CravingsLevel")
    private Integer cravingsLevel;

    @Column(name = "Mood")
    private String mood;

    @Column(name = "HealthNotes")
    private String healthNotes;

    @Column(name = "MoneySavedToday")
    private BigDecimal moneySavedToday;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public Boolean getSmokedToday() {
        return smokedToday;
    }

    public void setSmokedToday(Boolean smokedToday) {
        this.smokedToday = smokedToday;
    }

    public Integer getCigarettesSmoked() {
        return cigarettesSmoked;
    }

    public void setCigarettesSmoked(Integer cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }

    public Integer getCravingsLevel() {
        return cravingsLevel;
    }

    public void setCravingsLevel(Integer cravingsLevel) {
        this.cravingsLevel = cravingsLevel;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getHealthNotes() {
        return healthNotes;
    }

    public void setHealthNotes(String healthNotes) {
        this.healthNotes = healthNotes;
    }

    public BigDecimal getMoneySavedToday() {
        return moneySavedToday;
    }

    public void setMoneySavedToday(BigDecimal moneySavedToday) {
        this.moneySavedToday = moneySavedToday;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.SWP_Backend.dto;

import java.time.LocalDate;

public class DailyProgressRequest {
    private LocalDate logDate;
    private boolean smokedToday;
    private int cigarettesSmoked;
    private int cravingsLevel;
    private String mood;
    private String healthNotes;

    // NEW: user-entered money saved
    private double moneySavedToday;

    // getters and setters
    public LocalDate getLogDate() {
        return logDate;
    }
    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }
    public boolean isSmokedToday() {
        return smokedToday;
    }
    public void setSmokedToday(boolean smokedToday) {
        this.smokedToday = smokedToday;
    }
    public int getCigarettesSmoked() {
        return cigarettesSmoked;
    }
    public void setCigarettesSmoked(int cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }
    public int getCravingsLevel() {
        return cravingsLevel;
    }
    public void setCravingsLevel(int cravingsLevel) {
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
    public double getMoneySavedToday() {
        return moneySavedToday;
    }
    public void setMoneySavedToday(double moneySavedToday) {
        this.moneySavedToday = moneySavedToday;
    }
}

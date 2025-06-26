package com.example.SWP_Backend.dto;


public class MonthlyConsultationDTO {
    private int year;
    private int month;
    private Long totalConsultations;

    public MonthlyConsultationDTO(int year, int month, Long totalConsultations) {
        this.year = year;
        this.month = month;
        this.totalConsultations = totalConsultations;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(Long totalConsultations) {
        this.totalConsultations = totalConsultations;
    }
}


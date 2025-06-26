package com.example.SWP_Backend.dto;

// MonthlyRevenueDTO.java

public class MonthlyRevenueDTO {
    private int year;
    private int month;
    private double total;

    public MonthlyRevenueDTO(int year, int month, double total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }
    // getters/setters

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

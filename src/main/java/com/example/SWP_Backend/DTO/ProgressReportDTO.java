package com.example.SWP_Backend.dto;


import java.math.BigDecimal;

public class ProgressReportDTO {
    private long daysQuit;
    private BigDecimal moneySaved;

    public ProgressReportDTO(long daysQuit, BigDecimal moneySaved) {
        this.daysQuit = daysQuit;
        this.moneySaved = moneySaved;
    }

    public long getDaysQuit() {
        return daysQuit;
    }

    public BigDecimal getMoneySaved() {
        return moneySaved;
    }
}


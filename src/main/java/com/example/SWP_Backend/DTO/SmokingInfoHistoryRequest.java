package com.example.SWP_Backend.dto;


public class SmokingInfoHistoryRequest {
    private int years;             // số năm hút
    private int cigarettesPerDay;  // số điếu/ngày
    //private int moneyPerDay;       // số tiền/ngày (có thể dùng hoặc không)
    private int soNgay;            // số ngày lập kế hoạch


    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(int cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public int getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }
}

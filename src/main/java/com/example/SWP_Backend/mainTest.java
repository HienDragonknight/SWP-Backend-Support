package com.example.SWP_Backend;

public class mainTest {
    public static String tinhMucDo(int years, int cigarettesPerDay) {
        double packYear = (cigarettesPerDay / 20.0) * years;
        if (packYear < 5) return "Nhẹ";
        else if (packYear < 20) return "Trung bình";
        else return "Nặng";
    }

    public static void main(String[] args) {
        System.out.println("Tinh Muc do " + tinhMucDo(20, 20));
    }
}

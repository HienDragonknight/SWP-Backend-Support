package com.example.SWP_Backend.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class AdminCreateCoachRequest {
    // Thông tin tài khoản
    private String email;
    private String password;
    private String fullName;
    // Thông tin hồ sơ coach
    private String specialization;
    private String degree;
    private String phoneNumber;
    private String gender;
    private String address;
    private String experience;
    private Double rating;
    private String bio;
    private String availability;
    private String profilePictureUrl;
    private boolean isActive;

}

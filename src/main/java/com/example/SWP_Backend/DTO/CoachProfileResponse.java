package com.example.SWP_Backend.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class CoachProfileResponse {
    private Long coachId;
    private Long userId;
    private String fullName;
    private String email;            // nếu cần
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
    // getter/setter...
}

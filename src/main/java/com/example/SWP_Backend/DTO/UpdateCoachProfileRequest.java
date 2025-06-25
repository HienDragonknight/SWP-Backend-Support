package com.example.SWP_Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCoachProfileRequest {
    // ==== Trường chung với User (ai cũng có) ====
    private String fullName;
    private String profilePictureUrl;
    private String phoneNumber;
    private String address;
    private String gender;
    private String hometown;    // Quê quán
    private String occupation;  // Nghề nghiệp
    private Integer age;        // Tuổi

    // ==== Trường riêng cho Coach ====
    private String specialization;
    private String degree;
    private String experience;
    private Double rating;
    private String bio;
    private String availability;
}

package com.example.SWP_Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private Long userId;
    private String fullName;
    private String profilePictureUrl;
    private Long coachId;
    private Integer currentMembershipPackageId;

    // ======= Các trường bổ sung =======
    private String phoneNumber;
    private String hometown;
    private String occupation;
    private Integer age;
    private String address;
    private String gender; // <-- Thêm trường gender ở đây

    // ======= Getters and Setters =======
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }

    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }

    public Integer getCurrentMembershipPackageId() { return currentMembershipPackageId; }
    public void setCurrentMembershipPackageId(Integer currentMembershipPackageId) { this.currentMembershipPackageId = currentMembershipPackageId; }

    // --- Trường mới ---
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getHometown() { return hometown; }
    public void setHometown(String hometown) { this.hometown = hometown; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // --- Getter/Setter cho gender ---
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}

package com.example.SWP_Backend.DTO;

public class UpdateProfileRequest {
    private Long userId;
    private String fullName;
    private String profilePictureUrl;
    private Long coachId;
    private Integer currentMembershipPackageId;

    // Getters and Setters
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
}


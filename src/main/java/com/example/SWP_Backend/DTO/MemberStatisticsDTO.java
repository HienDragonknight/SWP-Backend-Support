package com.example.SWP_Backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatisticsDTO {
    private Long memberId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String status; // "Đang tư vấn" hoặc "Tạm dừng"
    private LocalDateTime lastConsultationDate;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastConsultationDate() {
        return lastConsultationDate;
    }

    public void setLastConsultationDate(LocalDateTime lastConsultationDate) {
        this.lastConsultationDate = lastConsultationDate;
    }
}


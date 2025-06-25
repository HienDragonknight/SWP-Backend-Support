package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CessationPlans")
public class CessationPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planID;
    @Nationalized
    private String reasonToQuit;
    @Nationalized
    private LocalDate startDate;
    private LocalDate targetQuitDate;
    private Integer cigarettesPerDay;
    @Nationalized
    private String smokingFrequency;
    private BigDecimal costPerPack;
    @Nationalized
    private String notes;
    @Nationalized
    private String customDetails;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    public Long getPlanID() {
        return planID;
    }

    public void setPlanID(Long planID) {
        this.planID = planID;
    }

    public String getReasonToQuit() {
        return reasonToQuit;
    }

    public void setReasonToQuit(String reasonToQuit) {
        this.reasonToQuit = reasonToQuit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetQuitDate() {
        return targetQuitDate;
    }

    public void setTargetQuitDate(LocalDate targetQuitDate) {
        this.targetQuitDate = targetQuitDate;
    }

    public Integer getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(Integer cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public String getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(String smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public BigDecimal getCostPerPack() {
        return costPerPack;
    }

    public void setCostPerPack(BigDecimal costPerPack) {
        this.costPerPack = costPerPack;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCustomDetails() {
        return customDetails;
    }

    public void setCustomDetails(String customDetails) {
        this.customDetails = customDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

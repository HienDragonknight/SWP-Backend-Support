package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SmokingStatusLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmokingStatusLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    private LocalDate logDate;
    private Integer cigarettesPerDay;
    @Nationalized
    private String smokingFrequency;
    private BigDecimal costPerPack;
    @Nationalized
    private String notes;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
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
}

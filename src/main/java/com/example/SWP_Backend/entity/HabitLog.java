package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HabitLogs")
public class HabitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate logDate = LocalDate.now();

    @Column(nullable = false)
    private Boolean smokedToday; // true = có hút, false = không hút

    private Integer cigarettesSmoked;

    private Integer cravingsLevel;

    private String mood;

    private String notes;

    /**
     * Số tiền tiết kiệm được hôm nay.
     * Nếu bạn không hút thuốc hôm nay thì user tự nhập vào,
     * còn nếu bạn hút thuốc thì có thể set 0.
     * Cấu hình columnDefinition để có DEFAULT 0 tránh lỗi ALTER TABLE.
     */
    @Column(
            name = "money_saved",
            nullable = false,
            columnDefinition = "float default 0"
    )
    private double moneySaved = 0.0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public Boolean getSmokedToday() {
        return smokedToday;
    }

    public void setSmokedToday(Boolean smokedToday) {
        this.smokedToday = smokedToday;
    }

    public Integer getCigarettesSmoked() {
        return cigarettesSmoked;
    }

    public void setCigarettesSmoked(Integer cigarettesSmoked) {
        this.cigarettesSmoked = cigarettesSmoked;
    }

    public Integer getCravingsLevel() {
        return cravingsLevel;
    }

    public void setCravingsLevel(Integer cravingsLevel) {
        this.cravingsLevel = cravingsLevel;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(double moneySaved) {
        this.moneySaved = moneySaved;
    }
}

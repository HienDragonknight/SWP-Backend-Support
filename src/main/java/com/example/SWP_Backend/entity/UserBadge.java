package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data

@Builder
@Table(name = "UserBadges")
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;

    @Column(nullable = false)
    private String badgeName;

    @Column(nullable = false)
    private String description;

    public UserBadge(Long id, Integer userId, String badgeName, String description) {
        this.id = id;
        this.userId = userId;
        this.badgeName = badgeName;
        this.description = description;
    }

    public UserBadge() {
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

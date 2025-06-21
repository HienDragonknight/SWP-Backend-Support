package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CessationPlanDetail")
public class CessationPlanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PlanID", nullable = false)
    private CessationPlan plan;

    @Column(nullable = false)
    private Integer day; // Số ngày trong plan

    private String goal;
    private String activity1;
    private String activity2;
    private String activity3;
    private String activity4;
    private String activity5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CessationPlan getPlan() {
        return plan;
    }

    public void setPlan(CessationPlan plan) {
        this.plan = plan;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivity1() {
        return activity1;
    }

    public void setActivity1(String activity1) {
        this.activity1 = activity1;
    }

    public String getActivity2() {
        return activity2;
    }

    public void setActivity2(String activity2) {
        this.activity2 = activity2;
    }

    public String getActivity3() {
        return activity3;
    }

    public void setActivity3(String activity3) {
        this.activity3 = activity3;
    }

    public String getActivity4() {
        return activity4;
    }

    public void setActivity4(String activity4) {
        this.activity4 = activity4;
    }

    public String getActivity5() {
        return activity5;
    }

    public void setActivity5(String activity5) {
        this.activity5 = activity5;
    }
}

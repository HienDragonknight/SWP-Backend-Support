package com.example.SWP_Backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PlanStages")
public class PlanStage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageID;

    @ManyToOne
    @JoinColumn(name = "PlanID", nullable = false)
    private CessationPlan plan;

    private String stageName;
    private String description;
    private Integer targetDurationDays;
    private Integer sequenceOrder;

    public Long getStageID() {
        return stageID;
    }

    public void setStageID(Long stageID) {
        this.stageID = stageID;
    }

    public CessationPlan getPlan() {
        return plan;
    }

    public void setPlan(CessationPlan plan) {
        this.plan = plan;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTargetDurationDays() {
        return targetDurationDays;
    }

    public void setTargetDurationDays(Integer targetDurationDays) {
        this.targetDurationDays = targetDurationDays;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }
}

package com.example.SWP_Backend.DTO;

import lombok.Data;

@Data
public class PlanStageRequest {
    private Long planId;
    private String stageName;
    private String description;
    private Integer targetDurationDays;
    private Integer sequenceOrder;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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


package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "UserPlanStageProgress")
public class UserPlanStageProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer sequenceOrder; // Số thứ tự giai đoạn

    @Column(nullable = false)
    private boolean completed = false;

    // Nếu bạn không dùng Lombok thì tự khai báo:
     public UserPlanStageProgress() {
     }

  //   Và constructor đầy đủ:
//     public UserPlanStageProgress(Long planStageId, Integer userId, Boolean completed) {
//         this.planStageId = planStageId;
//         this.userId = userId;
//         this.completed = completed;
//     }
    // ✅ Thêm constructor không id
    public UserPlanStageProgress(Long userId, Integer sequenceOrder, Boolean completed) {
        this.userId = userId;
        this.sequenceOrder = sequenceOrder;
        this.completed = completed;
    }


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

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

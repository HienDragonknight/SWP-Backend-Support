package com.example.SWP_Backend.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

/**
 * Feedback entity đại diện cho đánh giá/báo cáo/feedback giữa user, coach và admin.
 */
@Entity
@Table(name = "Feedback")
public class Feedback {

    /** Khóa chính tự tăng của feedback */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    /** User gửi feedback này (có thể là user thường hoặc coach) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    /**
     * Loại đối tượng được feedback/báo cáo
     * (ví dụ: "Coach", "System", "BlogPosts", "ProgressReport")
     */
    @Column(nullable = false, length = 255)
    @Nationalized
    private String targetType;

    /** ID đối tượng nhận feedback (CoachId, UserId, hoặc BlogPostId,...) */
    @Column(nullable = false)
    private Long targetId;

    /** Số điểm đánh giá (1-5) nếu là đánh giá, hoặc null nếu là báo cáo */
    private Integer rating;

    /** Nội dung phản hồi/đánh giá */
    @Column(columnDefinition = "NVARCHAR(MAX)")
    @Nationalized
    private String comment;

    /** Ngày gửi feedback */
    @Column(nullable = false)
    private LocalDateTime submissionDate = LocalDateTime.now();

    // ======= Các trường mở rộng nhóm bạn yêu cầu =======

    /** Tiêu đề feedback/báo cáo */
    @Column(length = 255)
    @Nationalized
    private String title;

    /** Phản hồi xử lý từ admin/coach cho feedback này */
    @Column(columnDefinition = "NVARCHAR(MAX)")
    @Nationalized
    private String respon;

    /** Trạng thái xử lý feedback: pending, done, rejected, ... */
    @Column(length = 255)
    @Nationalized
    private String status;

    // ====== GETTER/SETTER, Constructors ====== //
    public Feedback() {}

    public Long getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Long feedbackId) { this.feedbackId = feedbackId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRespon() { return respon; }
    public void setRespon(String respon) { this.respon = respon; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

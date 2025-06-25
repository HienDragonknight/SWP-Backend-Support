package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO cho Feedback – trả về đánh giá/góp ý/báo cáo
 */
@Data
public class FeedbackDTO {
    /** Mã feedback */
    private Long feedbackId;
    /** Mã người gửi feedback */
    private Long userId;
    /** Tên người gửi feedback */
    private String userFullName;
    /** Đối tượng được feedback (ví dụ: coach, system, blogpost, ...) */
    private String targetType;
    /** ID đối tượng được feedback */
    private Long targetId;
    /** Điểm đánh giá (1-5), nếu có */
    private Integer rating;
    /** Nội dung đánh giá/góp ý */
    private String comment;
    /** Ngày gửi feedback */
    private String submissionDate;
    /** Tiêu đề feedback */
    private String title;
    /** Phản hồi xử lý từ admin/coach */
    private String respon;
    /** Trạng thái xử lý (pending, done, rejected, ...) */
    private String status;
}

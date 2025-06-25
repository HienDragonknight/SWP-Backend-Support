package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO nhận vào khi tạo hoặc cập nhật feedback (đánh giá/báo cáo).
 * Chỉ chứa các trường cần thiết từ FE.
 */
@Data
public class FeedbackCreateRequest {
    /** ID user gửi feedback (bắt buộc) */
    private Long userId;

    /** Loại đối tượng được feedback ("Coach", "System", "BlogPosts", ...) */
    private String targetType;

    /** ID đối tượng được feedback (ví dụ: coachId, blogPostId, ...) */
    private Long targetId;

    /** Số điểm đánh giá (1-5), có thể null nếu là báo cáo */
    private Integer rating;

    /** Nội dung bình luận/đánh giá/báo cáo */
    private String comment;

    /** Tiêu đề feedback/báo cáo (có thể để trống) */
    private String title;

    /** Phản hồi xử lý từ admin/coach (người dùng không cần truyền khi tạo mới) */
    private String respon;

    /** Trạng thái xử lý feedback: "pending", "done", "rejected", ... (nếu FE cần truyền) */
    private String status;
}

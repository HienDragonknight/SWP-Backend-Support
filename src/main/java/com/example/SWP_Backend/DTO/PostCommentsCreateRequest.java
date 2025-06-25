package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO nhận vào khi tạo hoặc cập nhật bình luận (PostComments).
 * Chỉ chứa các trường cần thiết từ FE.
 */
@Data
public class PostCommentsCreateRequest {
    /** ID bài viết mà bình luận này thuộc về */
    private Long postId;

    /** ID user thực hiện bình luận */
    private Long userId;

    /** ID comment cha (nếu là reply, có thể để null nếu là comment gốc) */
    private Long parentCommentId;

    /** Nội dung bình luận */
    private String content;

    /** Đã được duyệt chưa (mặc định true, FE có thể không truyền) */
    private Boolean isApproved;

    /** Số upvote ban đầu (nếu cho phép FE truyền vào, thường để mặc định 0) */
    private Integer upvotes;

    /** Số downvote ban đầu (nếu cho phép FE truyền vào, thường để mặc định 0) */
    private Integer downvotes;
}

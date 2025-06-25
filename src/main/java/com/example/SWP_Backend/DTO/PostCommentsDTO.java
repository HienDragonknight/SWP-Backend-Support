package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO cho PostComments – trả về dữ liệu bình luận cho FE
 */
@Data
public class PostCommentsDTO {
    /** Mã bình luận */
    private Long commentId;
    /** Mã bài viết */
    private Long postId;
    /** Mã người bình luận */
    private Long userId;
    /** Tên người bình luận */
    private String userFullName;
    /** Mã bình luận cha (nếu là trả lời bình luận khác) */
    private Long parentCommentId;
    /** Nội dung bình luận */
    private String content;
    /** Ngày bình luận */
    private String commentDate;
    /** Đã được duyệt chưa */
    private Boolean isApproved;
    /** Số lượt upvote */
    private Integer upvotes;
    /** Số lượt downvote */
    private Integer downvotes;
}

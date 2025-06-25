package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO nhận vào khi tạo hoặc cập nhật bài viết cộng đồng (BlogPosts).
 * Chỉ chứa các trường cần thiết từ FE.
 */
@Data
public class BlogPostsCreateRequest {
    /** Mã user (tác giả) của bài viết */
    private Long authorId;

    /** Tiêu đề bài viết */
    private String title;

    /** Slug đường dẫn duy nhất của bài viết (không dấu, viết liền) */
    private String slug;

    /** Nội dung bài viết */
    private String content;

    /** Tóm tắt nội dung (có thể để trống) */
    private String excerpt;

    /** Danh mục bài viết (có thể để trống) */
    private String category;

    /** Danh sách tag (cách nhau bởi dấu phẩy, có thể để trống) */
    private String tags;

    /** Đường dẫn ảnh đại diện (có thể để trống) */
    private String featuredImageURL;

    /** Trạng thái bài viết ("draft", "published", ...) */
    private String status;
}

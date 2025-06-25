package com.example.SWP_Backend.dto;

import lombok.Data;

/**
 * DTO cho BlogPosts – chỉ trả về các trường cơ bản cho FE
 */
@Data // Lombok tự động tạo getter, setter, toString, v.v.
public class BlogPostsDTO {
    /** Mã bài viết */
    private Long postId;
    /** Mã người đăng bài */
    private Long authorId;
    /** Tên người đăng bài */
    private String authorName;
    /** Tiêu đề bài viết */
    private String title;
    /** Slug (đường dẫn) */
    private String slug;
    /** Tóm tắt bài viết */
    private String excerpt;
    /** Ngày đăng */
    private String publishDate;
    /** Danh mục */
    private String category;
    /** Trạng thái (draft, pending, published, ...) */
    private String status;
    /** Số lượt xem */
    private Integer views;
    /** Đường dẫn ảnh nổi bật */
    private String featuredImageURL;
}

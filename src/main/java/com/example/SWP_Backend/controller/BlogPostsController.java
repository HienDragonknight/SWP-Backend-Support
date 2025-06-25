package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.BlogPostsDTO;
import com.example.SWP_Backend.dto.BlogPostsCreateRequest;
import com.example.SWP_Backend.entity.BlogPosts;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.UserRepository;
import com.example.SWP_Backend.service.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller cho API quản lý bài viết cộng đồng (BlogPosts) – nhận vào CreateRequest, trả về DTO.
 */
@RestController
@RequestMapping("/api/blogposts")
public class BlogPostsController {

    @Autowired
    private BlogPostsService blogPostsService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Tạo mới bài viết:
     * - Nhận vào BlogPostsCreateRequest (gọn, chỉ trường cần thiết)
     * - Trả về BlogPostsDTO (dữ liệu gọn cho FE)
     */
    @PostMapping
    public BlogPostsDTO createBlogPost(@RequestBody BlogPostsCreateRequest req) {
        // Tìm User (author) theo authorId từ request
        User author = userRepository.findById(req.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getAuthorId()));

        // Tạo entity BlogPosts từ request
        BlogPosts post = new BlogPosts();
        post.setAuthor(author);
        post.setTitle(req.getTitle());
        post.setSlug(req.getSlug());
        post.setContent(req.getContent());
        post.setExcerpt(req.getExcerpt());
        post.setCategory(req.getCategory());
        post.setTags(req.getTags());
        post.setFeaturedImageURL(req.getFeaturedImageURL());
        post.setStatus(req.getStatus());
        post.setPublishDate(LocalDateTime.now());
        post.setViews(0);

        // Gọi service lưu và trả về DTO
        return blogPostsService.createBlogPost(post);
    }

    /**
     * Lấy toàn bộ bài viết, trả về list DTO.
     */
    @GetMapping
    public List<BlogPostsDTO> getAllBlogPosts() {
        return blogPostsService.getAllBlogPosts();
    }

    /**
     * Lấy bài viết theo ID, trả về DTO.
     */
    @GetMapping("/{id}")
    public BlogPostsDTO getBlogPostById(@PathVariable Long id) {
        return blogPostsService.getBlogPostById(id)
                .orElseThrow(() -> new RuntimeException("BlogPost not found with id " + id));
    }

    /**
     * Cập nhật bài viết theo ID:
     * - Nhận vào BlogPostsCreateRequest
     * - Trả về BlogPostsDTO
     */
    @PutMapping("/{id}")
    public BlogPostsDTO updateBlogPost(@PathVariable Long id, @RequestBody BlogPostsCreateRequest req) {
        // Lấy User (author) từ id trong request
        User author = userRepository.findById(req.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getAuthorId()));

        // Lấy entity BlogPosts cũ từ service (service trả về Optional<BlogPosts>)
        BlogPosts existingPost = blogPostsService.getEntityById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với id: " + id));

        // Cập nhật các trường từ request
        existingPost.setAuthor(author);
        existingPost.setTitle(req.getTitle());
        existingPost.setSlug(req.getSlug());
        existingPost.setContent(req.getContent());
        existingPost.setExcerpt(req.getExcerpt());
        existingPost.setCategory(req.getCategory());
        existingPost.setTags(req.getTags());
        existingPost.setFeaturedImageURL(req.getFeaturedImageURL());
        existingPost.setStatus(req.getStatus());
        existingPost.setLastModifiedDate(LocalDateTime.now());

        // Gọi service lưu lại và trả về DTO đã cập nhật
        return blogPostsService.updateBlogPost(id, existingPost);
    }

    /**
     * Xóa bài viết.
     */
    @DeleteMapping("/{id}")
    public void deleteBlogPost(@PathVariable Long id) {
        blogPostsService.deleteBlogPost(id);
    }
}

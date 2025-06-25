package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.BlogPosts;
import com.example.SWP_Backend.dto.BlogPostsDTO;
import com.example.SWP_Backend.repository.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service xử lý logic cho BlogPosts, chuyển đổi entity sang DTO để trả về cho FE.
 */
@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsRepository blogPostsRepository;

    // Trả về entity BlogPosts gốc, dùng cho update
    public Optional<BlogPosts> getEntityById(Long id) {
        return blogPostsRepository.findById(id);
    }



    /**
     * Tạo mới một bài viết, nhận entity BlogPosts, trả về DTO sau khi lưu.
     */
    public BlogPostsDTO createBlogPost(BlogPosts blogPost) {
        BlogPosts saved = blogPostsRepository.save(blogPost);
        return toDTO(saved);
    }

    /**
     * Lấy tất cả bài viết (danh sách DTO).
     */
    public List<BlogPostsDTO> getAllBlogPosts() {
        List<BlogPosts> posts = blogPostsRepository.findAll();
        // Chuyển từng entity sang DTO rồi trả về list
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Lấy bài viết theo ID (DTO).
     */
    public Optional<BlogPostsDTO> getBlogPostById(Long id) {
        return blogPostsRepository.findById(id).map(this::toDTO);
    }

    /**
     * Update bài viết, trả về DTO đã update.
     */
    public BlogPostsDTO updateBlogPost(Long id, BlogPosts updatedBlogPost) {
        return blogPostsRepository.findById(id).map(blogPost -> {
            blogPost.setTitle(updatedBlogPost.getTitle());
            blogPost.setSlug(updatedBlogPost.getSlug());
            blogPost.setContent(updatedBlogPost.getContent());
            blogPost.setExcerpt(updatedBlogPost.getExcerpt());
            blogPost.setCategory(updatedBlogPost.getCategory());
            blogPost.setTags(updatedBlogPost.getTags());
            blogPost.setViews(updatedBlogPost.getViews());
            blogPost.setStatus(updatedBlogPost.getStatus());
            blogPost.setFeaturedImageURL(updatedBlogPost.getFeaturedImageURL());
            blogPost.setLastModifiedDate(updatedBlogPost.getLastModifiedDate());
            BlogPosts saved = blogPostsRepository.save(blogPost);
            return toDTO(saved);
        }).orElseThrow(() -> new RuntimeException("BlogPost not found with id " + id));
    }

    /**
     * Xóa bài viết theo id.
     */
    public void deleteBlogPost(Long id) {
        blogPostsRepository.deleteById(id);
    }

    /**
     * Hàm chuyển đổi entity BlogPosts sang DTO.
     */
    public BlogPostsDTO toDTO(BlogPosts post) {
        BlogPostsDTO dto = new BlogPostsDTO();
        dto.setPostId(post.getPostId());
        dto.setAuthorId(post.getAuthor() != null ? post.getAuthor().getUserId() : null);
        dto.setAuthorName(post.getAuthor() != null ? post.getAuthor().getFullName() : null);
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setExcerpt(post.getExcerpt());
        dto.setPublishDate(post.getPublishDate() != null ? post.getPublishDate().toString() : null);
        dto.setCategory(post.getCategory());
        dto.setStatus(post.getStatus());
        dto.setViews(post.getViews());
        dto.setFeaturedImageURL(post.getFeaturedImageURL());
        return dto;
    }

}

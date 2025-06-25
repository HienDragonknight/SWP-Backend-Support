package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.BlogPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostsRepository extends JpaRepository<BlogPosts, Long> {
    // Có thể bổ sung các phương thức tìm kiếm theo status, author,...
}

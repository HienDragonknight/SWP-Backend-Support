package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentsRepository extends JpaRepository<PostComments, Long> {
    // Có thể bổ sung phương thức findByPost, findByUser, v.v.
}
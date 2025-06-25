package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.PostCommentsDTO;
import com.example.SWP_Backend.dto.PostCommentsCreateRequest;
import com.example.SWP_Backend.entity.PostComments;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.entity.BlogPosts;
import com.example.SWP_Backend.service.PostCommentsService;
import com.example.SWP_Backend.repository.UserRepository;
import com.example.SWP_Backend.repository.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller cho API quản lý bình luận bài viết (PostComments).
 */
@RestController
@RequestMapping("/api/comments")
public class PostCommentsController {

    @Autowired
    private PostCommentsService postCommentsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostsRepository blogPostsRepository;

    /** Tạo mới comment, nhận CreateRequest, trả về DTO. */
    @PostMapping
    public PostCommentsDTO createComment(@RequestBody PostCommentsCreateRequest req) {
        // Lấy user từ userId
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getUserId()));
        // Lấy bài viết từ postId
        BlogPosts post = blogPostsRepository.findById(req.getPostId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với id: " + req.getPostId()));
        // Nếu có parentCommentId thì lấy, không thì null
        PostComments parentComment = null;
        if (req.getParentCommentId() != null) {
            parentComment = postCommentsService.getEntityById(req.getParentCommentId())
                    .orElse(null); // cho phép null nếu là comment gốc
        }
        // Mapping sang entity
        PostComments comment = new PostComments();
        comment.setUser(user);
        comment.setPost(post);
        comment.setParentComment(parentComment);
        comment.setContent(req.getContent());
        comment.setCommentDate(LocalDateTime.now());
        comment.setIsApproved(true);
        comment.setUpvotes(0);
        comment.setDownvotes(0);

        // Gọi service lưu và trả về DTO
        return postCommentsService.createComment(comment);
    }

    /** Lấy toàn bộ comment, trả về list DTO. */
    @GetMapping
    public List<PostCommentsDTO> getAllComments() {
        return postCommentsService.getAllComments();
    }

    /** Lấy comment theo ID, trả về DTO. */
    @GetMapping("/{id}")
    public PostCommentsDTO getCommentById(@PathVariable Long id) {
        return postCommentsService.getCommentById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    /** Update comment theo ID, nhận CreateRequest, trả về DTO mới. */
    @PutMapping("/{id}")
    public PostCommentsDTO updateComment(@PathVariable Long id, @RequestBody PostCommentsCreateRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getUserId()));
        BlogPosts post = blogPostsRepository.findById(req.getPostId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với id: " + req.getPostId()));
        PostComments parentComment = null;
        if (req.getParentCommentId() != null) {
            parentComment = postCommentsService.getEntityById(req.getParentCommentId())
                    .orElse(null);
        }
        // Lấy entity comment gốc để sửa
        PostComments comment = postCommentsService.getEntityById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy comment với id: " + id));
        comment.setUser(user);
        comment.setPost(post);
        comment.setParentComment(parentComment);
        comment.setContent(req.getContent());
        comment.setCommentDate(LocalDateTime.now());
        // Có thể chỉnh sửa các trường khác nếu muốn

        return postCommentsService.updateComment(id, comment);
    }

    /** Xóa comment. */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        postCommentsService.deleteComment(id);
    }
}

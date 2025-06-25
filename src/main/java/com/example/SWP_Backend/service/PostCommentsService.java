package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.PostComments;
import com.example.SWP_Backend.dto.PostCommentsDTO;
import com.example.SWP_Backend.repository.PostCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service xử lý logic cho PostComments, trả về DTO.
 */
@Service
public class PostCommentsService {
    @Autowired
    private PostCommentsRepository postCommentsRepository;

    public Optional<PostComments> getEntityById(Long id) {
        return postCommentsRepository.findById(id);
    }


    /**
     * Tạo mới comment, trả về DTO.
     */
    public PostCommentsDTO createComment(PostComments comment) {
        PostComments saved = postCommentsRepository.save(comment);
        return toDTO(saved);
    }

    /**
     * Lấy tất cả comment (list DTO).
     */
    public List<PostCommentsDTO> getAllComments() {
        return postCommentsRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Lấy comment theo ID (DTO).
     */
    public Optional<PostCommentsDTO> getCommentById(Long id) {
        return postCommentsRepository.findById(id).map(this::toDTO);
    }

    /**
     * Update comment, trả về DTO.
     */
    public PostCommentsDTO updateComment(Long id, PostComments updatedComment) {
        return postCommentsRepository.findById(id).map(comment -> {
            comment.setContent(updatedComment.getContent());
            comment.setIsApproved(updatedComment.getIsApproved());
            comment.setUpvotes(updatedComment.getUpvotes());
            comment.setDownvotes(updatedComment.getDownvotes());
            PostComments saved = postCommentsRepository.save(comment);
            return toDTO(saved);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    /**
     * Xóa comment theo id.
     */
    public void deleteComment(Long id) {
        postCommentsRepository.deleteById(id);
    }

    /**
     * Chuyển entity comment sang DTO.
     */
    public PostCommentsDTO toDTO(PostComments comment) {
        PostCommentsDTO dto = new PostCommentsDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPost() != null ? comment.getPost().getPostId() : null);
        dto.setUserId(comment.getUser() != null ? comment.getUser().getUserId() : null);
        dto.setUserFullName(comment.getUser() != null ? comment.getUser().getFullName() : null);
        dto.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null);
        dto.setContent(comment.getContent());
        dto.setCommentDate(comment.getCommentDate() != null ? comment.getCommentDate().toString() : null);
        dto.setIsApproved(comment.getIsApproved());
        dto.setUpvotes(comment.getUpvotes());
        dto.setDownvotes(comment.getDownvotes());
        return dto;
    }
}

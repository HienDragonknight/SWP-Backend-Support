package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.FeedbackDTO;
import com.example.SWP_Backend.dto.FeedbackCreateRequest;
import com.example.SWP_Backend.entity.Feedback;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.service.FeedbackService;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller cho API quản lý Feedback.
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserRepository userRepository;

    /** Tạo mới feedback, trả về DTO. */
    @PostMapping
    public FeedbackDTO createFeedback(@RequestBody FeedbackCreateRequest req) {
        // Lấy user từ userId trong request
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getUserId()));

        // Mapping từ request sang entity
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setTargetType(req.getTargetType());
        feedback.setTargetId(req.getTargetId());
        feedback.setRating(req.getRating());
        feedback.setComment(req.getComment());
        feedback.setTitle(req.getTitle());
        feedback.setRespon(req.getRespon());
        feedback.setStatus(req.getStatus());
        feedback.setSubmissionDate(LocalDateTime.now());

        // Gọi service lưu và trả về DTO
        return feedbackService.createFeedback(feedback);
    }

    /** Lấy toàn bộ feedback, trả về list DTO. */
    @GetMapping
    public List<FeedbackDTO> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    /** Lấy feedback theo ID, trả về DTO. */
    @GetMapping("/{id}")
    public FeedbackDTO getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id " + id));
    }

    /** Update feedback theo ID, trả về DTO mới. */
    @PutMapping("/{id}")
    public FeedbackDTO updateFeedback(@PathVariable Long id, @RequestBody FeedbackCreateRequest req) {
        // Lấy user từ userId trong request
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + req.getUserId()));

        // Lấy entity Feedback gốc từ service (giống như BlogPosts)
        Feedback feedback = feedbackService.getEntityById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy feedback với id: " + id));

        // Cập nhật các trường từ request
        feedback.setUser(user);
        feedback.setTargetType(req.getTargetType());
        feedback.setTargetId(req.getTargetId());
        feedback.setRating(req.getRating());
        feedback.setComment(req.getComment());
        feedback.setTitle(req.getTitle());
        feedback.setRespon(req.getRespon());
        feedback.setStatus(req.getStatus());
        feedback.setSubmissionDate(LocalDateTime.now());

        // Gọi service lưu lại và trả về DTO mới
        return feedbackService.updateFeedback(id, feedback);
    }

    /** Xóa feedback. */
    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
    }
}

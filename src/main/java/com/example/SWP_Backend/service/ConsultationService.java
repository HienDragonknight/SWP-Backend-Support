package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.ConsultationRequest;
import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.entity.Consultation;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.CoachRepository;
import com.example.SWP_Backend.repository.ConsultationRepository;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Tạo yêu cầu tư vấn mới sau khi xác thực user và coach có tồn tại.
     */
    public Consultation createConsultation(ConsultationRequest request) {
        // Kiểm tra người dùng
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User ID không tồn tại: " + request.getUserId()));

        // Kiểm tra huấn luyện viên
        Coach coach = coachRepository.findById(request.getCoachId())
                .orElseThrow(() -> new IllegalArgumentException("Coach ID không tồn tại: " + request.getCoachId()));

        if (!coach.isActive()) {
            throw new IllegalArgumentException("Huấn luyện viên hiện không hoạt động.");
        }

        // Kiểm tra thời gian
        if (request.getScheduledTime() == null || request.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Thời gian tư vấn không hợp lệ.");
        }

        // Tạo mới cuộc hẹn
        Consultation consultation = new Consultation();
        consultation.setUserId(user.getUserId());
        consultation.setCoachId(coach.getCoachId());
        consultation.setScheduledTime(request.getScheduledTime());
        consultation.setNotes(request.getNotes());
        consultation.setStatus("pending");
        consultation.setMeetingLink(null); // Chưa xác nhận

        return consultationRepository.save(consultation);
    }


    /**
     * Coach xác nhận và dán link Google Meet.
     */
    public Consultation updateMeetingLinkAndStatus(Long id, String meetingLink, String status) {
        Consultation c = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + id));

        c.setMeetingLink(meetingLink);
        c.setStatus(status);

        return consultationRepository.save(c);
    }

    /**
     * Lấy danh sách lịch tư vấn theo User.
     */
    public List<Consultation> getByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        return consultationRepository.findByUserId(userId);
    }

    /**
     * Lấy danh sách lịch tư vấn theo Coach.
     */
    public List<Consultation> getByCoachId(Long coachId) {
        if (!coachRepository.existsById(coachId)) {
            throw new RuntimeException("Coach not found with ID: " + coachId);
        }
        return consultationRepository.findByCoachId(coachId);
    }
}

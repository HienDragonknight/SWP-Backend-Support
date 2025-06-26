package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.CoachStatisticsDTO;
import com.example.SWP_Backend.dto.ConsultationRequest;
import com.example.SWP_Backend.dto.MemberStatisticsDTO;
import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.entity.Consultation;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.CoachRepository;
import com.example.SWP_Backend.repository.ConsultationRepository;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Consultation> getConsultationsByCoachAndMemberName(Long coachId, String memberName) {
        // Tìm danh sách userId theo memberName
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(memberName);
        List<Long> userIds = users.stream().map(User::getUserId).toList();

        // Tìm consultation của coach này và thuộc userIds trên
        return consultationRepository.findByCoachIdAndUserIdIn(coachId, userIds);
    }
    public List<Consultation> getConsultationsByMemberName(String memberName) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(memberName);
        List<Long> userIds = users.stream().map(User::getUserId).toList();
        return consultationRepository.findByUserIdIn(userIds); // bạn thêm phương thức này
    }
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

    public List<MemberStatisticsDTO> getMembersByCoach(Long coachId) {
        return consultationRepository.findMembersByCoach(coachId);
    }


    public CoachStatisticsDTO getCoachStatistics(Long coachId) {
        List<Consultation> consultations = consultationRepository.findByCoachId(coachId);
        if (consultations.isEmpty()) {
            return new CoachStatisticsDTO(0, 0, 0, 0);
        }

        // Tổng số buổi tư vấn
        int totalConsultations = consultations.size();

        // Tổng số completed
        long totalCompleted = consultations.stream()
                .filter(c -> "approved".equalsIgnoreCase(c.getStatus()))
                .count();

        // Tổng số member (distinct userId)
        Set<Long> memberIds = consultations.stream()
                .map(Consultation::getUserId)
                .collect(Collectors.toSet());

        int totalMembers = memberIds.size();

        double successRate = totalConsultations == 0 ? 0 : (double) totalCompleted / totalConsultations * 100;

        // Tính thời gian trung bình giữa lần đầu và lần cuối của mỗi member
        double averageDurationDays = memberIds.stream().mapToLong(memberId -> {
            List<Consultation> memberConsults = consultations.stream()
                    .filter(c -> c.getUserId().equals(memberId))
                    .sorted(Comparator.comparing(Consultation::getScheduledTime))
                    .toList();

            if (memberConsults.size() <= 1) return 0L;

            LocalDateTime first = memberConsults.get(0).getScheduledTime();
            LocalDateTime last = memberConsults.get(memberConsults.size() - 1).getScheduledTime();

            return Duration.between(first, last).toDays();
        }).average().orElse(0);

        return new CoachStatisticsDTO(
                totalMembers,
                (int) totalCompleted,
                successRate,
                averageDurationDays
        );
    }
}

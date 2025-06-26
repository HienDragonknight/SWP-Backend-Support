package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.ConsultationRequest;
import com.example.SWP_Backend.entity.Consultation;
import com.example.SWP_Backend.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultations")
//@CrossOrigin("*")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping("/search-by-member")
    public ResponseEntity<List<Consultation>> searchByMember(
            @RequestParam Long coachId,
            @RequestParam String memberName) {
        return ResponseEntity.ok(
                consultationService.getConsultationsByCoachAndMemberName(coachId, memberName)
        );
    }


    // B1: User tạo yêu cầu tư vấn
    @PostMapping("/request")
    public ResponseEntity<?> requestConsultation(@RequestBody ConsultationRequest consultationRequest) {
        try {
            // Tạo cuộc hẹn tư vấn
            Consultation created = consultationService.createConsultation(consultationRequest);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Yêu cầu tư vấn đã được gửi. Vui lòng chờ huấn luyện viên xác nhận.",
                    "data", created
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Đã xảy ra lỗi khi gửi yêu cầu tư vấn."
            ));
        }
    }


    // B2: Coach xác nhận và gửi link
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveConsultation(
            @PathVariable Long id,
            @RequestParam String meetingLink
    ) {
        Consultation updated = consultationService.updateMeetingLinkAndStatus(id, meetingLink, "approved");
        return ResponseEntity.ok(updated);
    }

    // Optional: Lấy tư vấn của User
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(consultationService.getByUserId(userId));
    }

    // Optional: Lấy tư vấn của Coach
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<?> getByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(consultationService.getByCoachId(coachId));
    }
}

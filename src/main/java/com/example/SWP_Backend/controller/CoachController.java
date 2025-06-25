package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.AdminCreateCoachRequest;
import com.example.SWP_Backend.dto.CoachProfileResponse;
import com.example.SWP_Backend.dto.CreateCoachRequest;
import com.example.SWP_Backend.dto.UpdateCoachProfileRequest;
import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.UserRepository;
import com.example.SWP_Backend.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private UserRepository userRepository;

    // ============= Helper mapping Entity Coach -> Response DTO =============
    private CoachProfileResponse mapToCoachProfileResponse(Coach coach) {
        CoachProfileResponse dto = new CoachProfileResponse();
        dto.setCoachId(coach.getCoachId());
        dto.setUserId(coach.getUser() != null ? coach.getUser().getUserId() : null);
        dto.setFullName(coach.getFullName());
        dto.setEmail(coach.getUser() != null ? coach.getUser().getEmail() : null);
        dto.setSpecialization(coach.getSpecialization());
        dto.setDegree(coach.getDegree());
        dto.setPhoneNumber(coach.getPhoneNumber());
        dto.setGender(coach.getGender());
        dto.setAddress(coach.getAddress());
        dto.setExperience(coach.getExperience());
        dto.setRating(coach.getRating());
        dto.setBio(coach.getBio());
        dto.setAvailability(coach.getAvailability());
        dto.setProfilePictureUrl(coach.getProfilePictureUrl());
        dto.setActive(coach.isActive());
        return dto;
    }

    // ============= GET ALL =============
    @GetMapping("/all")
    public ResponseEntity<List<CoachProfileResponse>> getAllCoaches() {
        List<Coach> coaches = coachService.getAllCoaches();
        List<CoachProfileResponse> result = coaches.stream()
                .map(this::mapToCoachProfileResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // ============= GET BY ID =============
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable Long id) {
        Optional<Coach> coach = coachService.getCoachById(id);
        return coach.map(c -> ResponseEntity.ok(mapToCoachProfileResponse(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ============= Tạo coach (đã có user) =============
    @PostMapping("/create")
    public ResponseEntity<?> createCoach(@RequestBody CreateCoachRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy user có userId: " + request.getUserId());
        }

        // Đồng bộ các trường profile về cả User và Coach
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getProfilePictureUrl() != null) user.setProfilePictureUrl(request.getProfilePictureUrl());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        userRepository.save(user);

        Coach coach = new Coach();
        coach.setUser(user);
        coach.setFullName(request.getFullName());
        coach.setSpecialization(request.getSpecialization());
        coach.setDegree(request.getDegree());
        coach.setPhoneNumber(request.getPhoneNumber());
        coach.setGender(request.getGender());
        coach.setAddress(request.getAddress());
        coach.setExperience(request.getExperience());
        coach.setRating(request.getRating());
        coach.setBio(request.getBio());
        coach.setAvailability(request.getAvailability());
        coach.setProfilePictureUrl(request.getProfilePictureUrl());
        coach.setActive(request.isActive());
        Coach savedCoach = coachService.saveCoach(coach);

        return ResponseEntity.ok(mapToCoachProfileResponse(savedCoach));
    }

    /*
    * Giải thích logic thực tế
1. Sự khác biệt về mục đích
PUT /api/coaches/update/{id}:
Được thiết kế cho ADMIN (hoặc nhân viên quản lý) cập nhật hồ sơ của bất kỳ coach nào theo id (trường hợp update hồ sơ cho người khác). Có thể dùng trong dashboard quản lý coach, nơi admin/superuser muốn chỉnh toàn bộ thông tin coach, kể cả các trường mà coach bình thường không tự sửa được.

PUT /api/coaches/update-profile:
Dùng cho chính coach đó đăng nhập và tự chỉnh sửa hồ sơ cá nhân của mình (self-service).
Luôn đồng bộ dữ liệu với bảng user (vì coach và user là 1-1), giúp coach sửa mọi trường cá nhân liên quan (profile, info chuyên môn).
    * */


    // ============= Update coach (đồng bộ cả User) =============
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCoach(@PathVariable Long id, @RequestBody CreateCoachRequest request) {
        Optional<Coach> existingCoachOpt = coachService.getCoachById(id);
        if (existingCoachOpt.isEmpty()) return ResponseEntity.notFound().build();
        Coach existingCoach = existingCoachOpt.get();

        User user = existingCoach.getUser();
        // Đồng bộ các trường profile về cả User và Coach
        if (request.getFullName() != null) {
            existingCoach.setFullName(request.getFullName());
            user.setFullName(request.getFullName());
        }
        if (request.getProfilePictureUrl() != null) {
            existingCoach.setProfilePictureUrl(request.getProfilePictureUrl());
            user.setProfilePictureUrl(request.getProfilePictureUrl());
        }
        if (request.getPhoneNumber() != null) {
            existingCoach.setPhoneNumber(request.getPhoneNumber());
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getGender() != null) {
            existingCoach.setGender(request.getGender());
            user.setGender(request.getGender());
        }
        if (request.getAddress() != null) {
            existingCoach.setAddress(request.getAddress());
            user.setAddress(request.getAddress());
        }
        userRepository.save(user);

        // Update các trường chuyên môn (Coach-only)
        existingCoach.setSpecialization(request.getSpecialization());
        existingCoach.setDegree(request.getDegree());
        existingCoach.setExperience(request.getExperience());
        existingCoach.setRating(request.getRating());
        existingCoach.setBio(request.getBio());
        existingCoach.setAvailability(request.getAvailability());
        existingCoach.setActive(request.isActive());

        Coach updated = coachService.saveCoach(existingCoach);
        return ResponseEntity.ok(mapToCoachProfileResponse(updated));
    }

    // ============= Xóa coach =============
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id) {
        if (!coachService.existsById(id)) return ResponseEntity.notFound().build();
        coachService.deleteCoach(id);
        return ResponseEntity.ok().build();
    }

    // ============= Admin tạo coach (tạo luôn cả user) =============
    @PostMapping("/admin-create")
    public ResponseEntity<?> adminCreateCoach(@RequestBody AdminCreateCoachRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }

        // 1. Tạo user account
        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getEmail());
        user.setPasswordHash(req.getPassword()); // Nên hash password
        user.setFullName(req.getFullName());
        user.setRole("coach");
        user.setEnabled(true);
        // Đồng bộ các trường profile
        user.setProfilePictureUrl(req.getProfilePictureUrl());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setGender(req.getGender());
        user.setAddress(req.getAddress());
        userRepository.save(user);

        // 2. Tạo Coach profile
        Coach coach = new Coach();
        coach.setUser(user);
        coach.setFullName(req.getFullName());
        coach.setSpecialization(req.getSpecialization());
        coach.setDegree(req.getDegree());
        coach.setPhoneNumber(req.getPhoneNumber());
        coach.setGender(req.getGender());
        coach.setAddress(req.getAddress());
        coach.setExperience(req.getExperience());
        coach.setRating(req.getRating());
        coach.setBio(req.getBio());
        coach.setAvailability(req.getAvailability());
        coach.setProfilePictureUrl(req.getProfilePictureUrl());
        coach.setActive(req.isActive());
        Coach savedCoach = coachService.saveCoach(coach);

        return ResponseEntity.ok(mapToCoachProfileResponse(savedCoach));
    }

    // ===== COACH TỰ SỬA HỒ SƠ CÁ NHÂN =====
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateMyCoachProfile(
            @RequestBody UpdateCoachProfileRequest req,
            @RequestParam("userId") Long userId // Có thể lấy userId từ JWT/principal nếu có
    ) {
        // 1. Lấy User và kiểm tra quyền
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !"coach".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Chỉ coach mới được sửa hồ sơ coach!");
        }
        // 2. Lấy Coach profile
        Coach coach = coachService.getCoachByUserId(user.getUserId());
        if (coach == null) {
            return ResponseEntity.badRequest().body("Coach profile chưa được khởi tạo!");
        }

        // 3. Đồng bộ TRƯỜNG CHUNG giữa User & Coach
        if (req.getFullName() != null) {
            user.setFullName(req.getFullName());
            coach.setFullName(req.getFullName());
        }
        if (req.getProfilePictureUrl() != null) {
            user.setProfilePictureUrl(req.getProfilePictureUrl());
            coach.setProfilePictureUrl(req.getProfilePictureUrl());
        }
        if (req.getPhoneNumber() != null) {
            user.setPhoneNumber(req.getPhoneNumber());
            coach.setPhoneNumber(req.getPhoneNumber());
        }
        if (req.getAddress() != null) {
            user.setAddress(req.getAddress());
            coach.setAddress(req.getAddress());
        }
        if (req.getGender() != null) {
            user.setGender(req.getGender());
            coach.setGender(req.getGender());
        }
        if (req.getHometown() != null) user.setHometown(req.getHometown());
        if (req.getOccupation() != null) user.setOccupation(req.getOccupation());
        if (req.getAge() != null) user.setAge(req.getAge());

        // 4. Các trường riêng cho Coach (KHÔNG update vào User)
        if (req.getSpecialization() != null) coach.setSpecialization(req.getSpecialization());
        if (req.getDegree() != null) coach.setDegree(req.getDegree());
        if (req.getExperience() != null) coach.setExperience(req.getExperience());
        if (req.getRating() != null) coach.setRating(req.getRating());
        if (req.getBio() != null) coach.setBio(req.getBio());
        if (req.getAvailability() != null) coach.setAvailability(req.getAvailability());

        userRepository.save(user);
        coachService.saveCoach(coach);

        return ResponseEntity.ok(mapToCoachProfileResponse(coach));
    }
}

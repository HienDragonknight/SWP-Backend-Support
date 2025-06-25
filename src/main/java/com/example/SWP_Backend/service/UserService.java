package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.UpdateCoachProfileRequest;
import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.entity.Token;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.CoachRepository;
import com.example.SWP_Backend.repository.TokenRepository;
import com.example.SWP_Backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    CoachRepository coachRepository; // Thêm dòng này!

    @Autowired
    EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    // ========================= ĐĂNG KÝ OTP 2 BƯỚC =========================

    public void registerUserWithOtp(User user) {
        if (isUsernameExists(user.getUsername()) || isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Username or Email already exists!");
        }
        user.setEnabled(false);
        user.setRegistrationDate(LocalDateTime.now());

        String userJson;
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot serialize user data", e);
        }

        String otp = generateOtp();
        tokenRepository.findByEmailAndType(user.getEmail(), "REGISTER_OTP")
                .ifPresent(tokenRepository::delete);

        Token vt = new Token();
        vt.setToken(otp);
        vt.setEmail(user.getEmail());
        vt.setUserInfo(userJson);
        vt.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        vt.setType("REGISTER_OTP");
        tokenRepository.save(vt);

        emailService.sendOtpResetPassword(user.getEmail(), otp);
    }

    public boolean verifyOtpAndRegister(String email, String otp) {
        Optional<Token> vtOpt = tokenRepository.findByEmailAndType(email, "REGISTER_OTP");
        if (vtOpt.isEmpty()) return false;
        Token vt = vtOpt.get();

        if (!vt.getToken().equals(otp) || vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(vt);
            return false;
        }

        try {
            User user = objectMapper.readValue(vt.getUserInfo(), User.class);
            if (userRepository.existsByEmail(user.getEmail())) {
                tokenRepository.delete(vt);
                return false;
            }
            user.setEnabled(true);
            userRepository.save(user);
            tokenRepository.delete(vt);
            return true;
        } catch (Exception e) {
            tokenRepository.delete(vt);
            return false;
        }
    }

    // ======================= OTP cho QUÊN MẬT KHẨU ==========================

    private String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    public boolean sendPasswordResetOtp(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        String otp = generateOtp();
        tokenRepository.findByEmailAndType(email, "PASSWORD_RESET_OTP")
                .ifPresent(tokenRepository::delete);

        Token vt = new Token();
        vt.setToken(otp);
        vt.setEmail(email);
        vt.setUserInfo(newPassword);
        vt.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        vt.setType("PASSWORD_RESET_OTP");
        tokenRepository.save(vt);

        emailService.sendOtpResetPassword(email, otp);
        return true;
    }

    public boolean verifyOtpAndResetPassword(String email, String otp) {
        Optional<Token> vtOpt = tokenRepository.findByEmailAndType(email, "PASSWORD_RESET_OTP");
        if (vtOpt.isEmpty()) return false;
        Token vt = vtOpt.get();

        if (!vt.getToken().equals(otp) || vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(vt);
            return false;
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            tokenRepository.delete(vt);
            return false;
        }

        user.setPasswordHash(vt.getUserInfo());
        userRepository.save(user);

        tokenRepository.delete(vt);
        return true;
    }

    // ================== CRUD & CHECK ==================

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }

    public User createNewUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            user.setUsername(user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUserById(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().trim().isEmpty()) {
                        user.setUsername(updatedUser.getUsername());
                    }
                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().trim().isEmpty()) {
                        user.setEmail(updatedUser.getEmail());
                    }
                    if (updatedUser.getFullName() != null) {
                        user.setFullName(updatedUser.getFullName());
                    }
                    if (updatedUser.getProfilePictureUrl() != null) {
                        user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
                    }
                    if (updatedUser.getCurrentMembershipPackageId() != null) {
                        user.setCurrentMembershipPackageId(updatedUser.getCurrentMembershipPackageId());
                    }
                    if (updatedUser.getSubscriptionEndDate() != null) {
                        user.setSubscriptionEndDate(updatedUser.getSubscriptionEndDate());
                    }
                    // ====== SỬA ĐOẠN NÀY ======
                    if (updatedUser.getCoach() != null) { // Kiểu Coach object, không còn coachId
                        user.setCoach(updatedUser.getCoach());
                    }
                    if (updatedUser.getRole() != null && !updatedUser.getRole().trim().isEmpty()) {
                        user.setRole(updatedUser.getRole());
                    }
                    // ====== Các trường bổ sung ======
                    if (updatedUser.getPhoneNumber() != null) user.setPhoneNumber(updatedUser.getPhoneNumber());
                    if (updatedUser.getHometown() != null) user.setHometown(updatedUser.getHometown());
                    if (updatedUser.getOccupation() != null) user.setOccupation(updatedUser.getOccupation());
                    if (updatedUser.getAge() != null) user.setAge(updatedUser.getAge());
                    if (updatedUser.getAddress() != null) user.setAddress(updatedUser.getAddress());
                    if (updatedUser.getGender() != null) user.setGender(updatedUser.getGender());
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    public boolean deleteUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateLastLoginDate(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setLastLoginDate(LocalDateTime.now());
                    userRepository.save(user);
                });
    }

    public boolean updatePassword(Long userId, String newPasswordHash) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setPasswordHash(newPasswordHash);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Cập nhật profile User (và đồng bộ sang Coach nếu user này là coach).
     * Luôn đảm bảo dữ liệu giữa User và Coach nhất quán cho các trường profile chung.
     */
    public boolean updateUserProfile(
            Long userId,
            String fullName,
            String profilePictureUrl,
            Long coachId,
            Integer membershipId,
            String phoneNumber,
            String hometown,
            String occupation,
            Integer age,
            String address,
            String gender
    ) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();

        // =========== Cập nhật các trường profile bên User ===========
        if (fullName != null) user.setFullName(fullName);
        if (profilePictureUrl != null) user.setProfilePictureUrl(profilePictureUrl);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if (hometown != null) user.setHometown(hometown);
        if (occupation != null) user.setOccupation(occupation);
        if (age != null) user.setAge(age);
        if (address != null) user.setAddress(address);
        if (gender != null) user.setGender(gender);
        if (membershipId != null) user.setCurrentMembershipPackageId(membershipId);

        // =========== Cập nhật chọn/bỏ/đổi coach ===========
        if (coachId != null) {
            if (coachId == -1) {
                user.setCoach(null); // Bỏ coach
            } else {
                Coach coach = coachRepository.findById(coachId).orElse(null);
                user.setCoach(coach); // Gán coach mới
            }
        }

        userRepository.save(user);

        // =========== Nếu user là coach, đồng bộ profile sang Coach ===========
        if ("coach".equalsIgnoreCase(user.getRole())) {
            Coach coach = coachRepository.findByUserUserId(userId);
            if (coach != null) {
                if (fullName != null) coach.setFullName(fullName);
                if (profilePictureUrl != null) coach.setProfilePictureUrl(profilePictureUrl);
                if (phoneNumber != null) coach.setPhoneNumber(phoneNumber);
                if (address != null) coach.setAddress(address);
                if (gender != null) coach.setGender(gender);
                // ... thêm trường nếu cần
                coachRepository.save(coach);
            } else {
                // Không có coach profile, chỉ log cảnh báo (hoặc tạo mới nếu bạn muốn)
                System.out.println("Warning: User có role coach nhưng chưa có Coach profile!");
                // Hoặc: tự động tạo coach profile mới (nếu hợp lý với luồng nghiệp vụ)
            }
        }

        return true;
    }


    public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        if (!user.getPasswordHash().equals(currentPassword)) {
            return false;
        }

        user.setPasswordHash(newPassword);
        userRepository.save(user);
        return true;
    }

    // Lấy tất cả user đã chọn coach (không null)
    public List<User> getUsersWithCoach() {
        return userRepository.findByCoachIsNotNull();
    }

    // Lấy tất cả user theo coachId (tìm coach object trước)
    public List<User> getUsersByCoachId(Long coachId) {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        if (coach == null) return List.of();
        return userRepository.findByCoach(coach);
    }

    /**
     * Dùng cho coach cập nhật toàn bộ hồ sơ (cả trường chung & riêng coach)
     * Đồng bộ dữ liệu giữa bảng User & Coach.
     */
    public boolean updateCoachFullProfile(Long userId, UpdateCoachProfileRequest req) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();

        // ==== 1. Update trường chung ở User ====
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getProfilePictureUrl() != null) user.setProfilePictureUrl(req.getProfilePictureUrl());
        if (req.getPhoneNumber() != null) user.setPhoneNumber(req.getPhoneNumber());
        if (req.getAddress() != null) user.setAddress(req.getAddress());
        if (req.getGender() != null) user.setGender(req.getGender());
        if (req.getHometown() != null) user.setHometown(req.getHometown());
        if (req.getOccupation() != null) user.setOccupation(req.getOccupation());
        if (req.getAge() != null) user.setAge(req.getAge());

        userRepository.save(user);

        // ==== 2. Nếu user là coach, đồng bộ trường sang Coach ====
        if ("coach".equalsIgnoreCase(user.getRole())) {
            Coach coach = coachRepository.findByUserUserId(userId);
            if (coach != null) {
                // Trường chung (sync với User)
                if (req.getFullName() != null) coach.setFullName(req.getFullName());
                if (req.getProfilePictureUrl() != null) coach.setProfilePictureUrl(req.getProfilePictureUrl());
                if (req.getPhoneNumber() != null) coach.setPhoneNumber(req.getPhoneNumber());
                if (req.getAddress() != null) coach.setAddress(req.getAddress());
                if (req.getGender() != null) coach.setGender(req.getGender());
                // Trường riêng Coach
                if (req.getSpecialization() != null) coach.setSpecialization(req.getSpecialization());
                if (req.getDegree() != null) coach.setDegree(req.getDegree());
                if (req.getExperience() != null) coach.setExperience(req.getExperience());
                if (req.getRating() != null) coach.setRating(req.getRating());
                if (req.getBio() != null) coach.setBio(req.getBio());
                if (req.getAvailability() != null) coach.setAvailability(req.getAvailability());
                coachRepository.save(coach);
            } else {
                System.out.println("Warning: Coach profile chưa được khởi tạo cho userId = " + userId);
            }
        }
        return true;
    }

}
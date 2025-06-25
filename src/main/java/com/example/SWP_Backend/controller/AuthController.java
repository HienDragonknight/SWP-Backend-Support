package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.TokenRepository;
import com.example.SWP_Backend.repository.UserRepository;
import com.example.SWP_Backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý các thao tác xác thực người dùng như đăng ký, đăng nhập,
 * gửi và xác minh mã OTP cho đăng ký và đặt lại mật khẩu.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // ======== Đăng ký tài khoản sử dụng OTP xác minh ========

    /**
     * Gửi mã OTP tới email người dùng để bắt đầu quá trình đăng ký.
     */
    @PostMapping("/register-request")
    public ResponseEntity<Map<String, Object>> requestRegistration(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userService.isEmailExists(request.getEmail())) {
                response.put("success", false);
                response.put("message", "Email đã tồn tại trong hệ thống.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                response.put("success", false);
                response.put("message", "Mật khẩu xác nhận không trùng khớp.");
                return ResponseEntity.badRequest().body(response);
            }

            User tempUser = new User();
            tempUser.setFullName(request.getFullName());
            tempUser.setEmail(request.getEmail());
            tempUser.setPasswordHash(request.getPassword());
            tempUser.setUsername(request.getEmail());
            tempUser.setRole("member");

            userService.registerUserWithOtp(tempUser);

            response.put("success", true);
            response.put("message", "OTP đã được gửi đến email của bạn. Hãy xác minh để hoàn tất đăng ký.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Đã xảy ra lỗi trong quá trình đăng ký: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Xác minh mã OTP để hoàn tất đăng ký tài khoản.
     */
    @PostMapping("/register-verify-otp")
    public ResponseEntity<Map<String, Object>> confirmRegistrationOtp(@RequestBody VerifyOtpRequest request) {
        Map<String, Object> response = new HashMap<>();
        boolean verified = userService.verifyOtpAndRegister(request.getEmail(), request.getOtp());
        if (verified) {
            response.put("success", true);
            response.put("message", "Đăng ký thành công. Bạn đã có thể đăng nhập.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Mã OTP không hợp lệ hoặc đã hết hạn.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ======== Xác thực đăng nhập ========

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserByEmail(request.getEmail());

            if (user == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy tài khoản với email này.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!user.isEnabled()) {
                response.put("success", false);
                response.put("message", "Tài khoản chưa được xác minh hoặc chưa hoàn tất đăng ký.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!user.getPasswordHash().equals(request.getPassword())) {
                response.put("success", false);
                response.put("message", "Sai mật khẩu.");
                return ResponseEntity.badRequest().body(response);
            }

            userService.updateLastLoginDate(user.getUserId());

            response.put("success", true);
            response.put("message", "Đăng nhập thành công.");
            response.put("user", Map.of(
                    "id", user.getUserId(),
                    "fullName", user.getFullName(),
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "profilePictureUrl", user.getProfilePictureUrl() != null ? user.getProfilePictureUrl() : ""
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi trong quá trình đăng nhập: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ======== Đặt lại mật khẩu bằng OTP ========

    /**
     * Gửi mã OTP đặt lại mật khẩu tới email người dùng.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> requestPasswordReset(@RequestBody ForgotPasswordOtpRequest req) {
        userService.sendPasswordResetOtp(req.getEmail(), req.getNewPassword());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Nếu email hợp lệ, mã OTP đã được gửi đến hòm thư của bạn."
        ));
    }

    /**
     * Xác minh mã OTP để đặt lại mật khẩu mới.
     */
    @PostMapping("/reset-password-otp")
    public ResponseEntity<Map<String, Object>> confirmPasswordReset(@RequestBody VerifyOtpRequest req) {
        boolean success = userService.verifyOtpAndResetPassword(req.getEmail(), req.getOtp());
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Mật khẩu đã được cập nhật thành công."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "OTP không hợp lệ hoặc đã hết hạn."));
        }
    }

    // ======== DTO nội bộ ========

    public static class RegisterRequest {
        private String fullName;
        private String email;
        private String password;
        private String confirmPassword;
        // Getters and setters
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getConfirmPassword() { return confirmPassword; }
        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    }

    public static class LoginRequest {
        private String email;
        private String password;
        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class ForgotPasswordOtpRequest {
        private String email;
        private String newPassword;
        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    public static class VerifyOtpRequest {
        private String email;
        private String otp;
        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }
}

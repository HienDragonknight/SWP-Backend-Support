package com.example.SWP_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // Khóa chính UserID (tự tăng)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private long userId;

    @Column(name = "Username", nullable = false, unique = true, length = 255)
    @Nationalized
    private String username; // Tên đăng nhập

    @Column(name = "PasswordHash", nullable = false, length = 255)
    private String passwordHash; // Mật khẩu hash

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email; // Email

    @Column(name = "FullName", length = 255)
    @Nationalized
    private String fullName; // Họ tên (nếu cần)

    @Column(name = "RegistrationDate", nullable = false)
    private LocalDateTime registrationDate; // Ngày đăng ký

    @Column(name = "LastLoginDate")
    private LocalDateTime lastLoginDate; // Ngày đăng nhập gần nhất

    @Column(name = "ProfilePictureURL", length = 255)
    private String profilePictureUrl; // Ảnh đại diện

    @Column(name = "CurrentMembershipPackageID")
    private Integer currentMembershipPackageId; // Gói thành viên hiện tại (FK)

    @Column(name = "SubscriptionEndDate")
    private LocalDate subscriptionEndDate; // Ngày hết hạn gói

    /**
     * Quan hệ nhiều người dùng (member) -> 1 coach
     * Nhiều user có thể cùng trỏ về 1 coach.
     * (Nếu user chưa chọn coach thì trường này có thể null)
     */
    @ManyToOne
    @JoinColumn(name = "CoachID")
    private Coach coach;

    @Column(name = "Role", nullable = false, length = 255)
    private String role = "member"; // member | coach | admin | ...

    @Column(name = "Enabled", nullable = false)
    private boolean enabled = false; // Trạng thái tài khoản

    // ========== Các trường bổ sung cho member (hoặc dùng cho coach nếu muốn) ==========

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "Hometown", length = 255)
    @Nationalized
    private String hometown;

    @Column(name = "Occupation", length = 255)
    @Nationalized
    private String occupation;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "Address", length = 255)
    @Nationalized
    private String address;

    @Column(name = "Gender", length = 20)
    @Nationalized
    private String gender; // Có thể String (Nam/Nữ), hoặc Integer nếu dùng code

    /**
     * Thiết lập mặc định khi tạo mới user
     */
    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
        if (role == null || role.trim().isEmpty()) {
            role = "member";
        }
        if (username == null || username.trim().isEmpty()) {
            username = email;
        }
    }
}
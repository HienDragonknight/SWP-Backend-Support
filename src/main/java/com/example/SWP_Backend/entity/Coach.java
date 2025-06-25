package com.example.SWP_Backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Coaches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coach {

    // Khóa chính CoachID (tự tăng)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CoachID")
    private Long coachId;

    /**
     * Quan hệ 1-1 với User (chỉ user nào có role là 'coach' thì sẽ có dòng profile này)
     * Coach profile chỉ là phần mở rộng thông tin cho user.
     */
    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", unique = true)
    private User user;

    // ========== Thông tin chuyên biệt cho Coach ==========

    @Column(name = "FullName", nullable = false)
    @Nationalized
    private String fullName; // Họ tên huấn luyện viên

    @Column(name = "Specialization")
    @Nationalized
    private String specialization; // Chuyên môn (ví dụ: cai thuốc, tâm lý...)

    @Column(name = "Degree")
    @Nationalized
    private String degree; // Bằng cấp

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber; // Số điện thoại liên hệ

    @Column(name = "Gender", length = 10)
    @Nationalized
    private String gender; // Giới tính

    @Column(name = "Address")
    @Nationalized
    private String address; // Địa chỉ

    @Column(name = "Experience")
    @Nationalized
    private String experience; // Kinh nghiệm

    @Column(name = "Rating")
    private Double rating; // Đánh giá trung bình

    @Column(name = "Bio")
    @Nationalized
    private String bio; // Giới thiệu bản thân

    @Column(name = "Availability")
    @Nationalized
    private String availability; // Lịch làm việc hoặc trạng thái sẵn sàng

    @Column(name = "ProfilePictureURL")
    private String profilePictureUrl; // Ảnh đại diện

    @Column(name = "IsActive", nullable = false)
    private boolean isActive = true; // Trạng thái hoạt động

    /**
     * Nếu muốn truy xuất danh sách member do coach này quản lý:
     * (Chỉ cần nếu thường xuyên lấy danh sách member của coach)
     * Chỉ cần thêm trường này vào, JPA sẽ tự join theo mappedBy = "coach" ở User.
     */
    // @OneToMany(mappedBy = "coach")
    // private List<User> members;

    // Lombok đã generate getter/setter, constructor đầy đủ
}


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
@Table(name = "Payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    // Thông tin người dùng mua gói
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    // Thông tin gói thành viên
    @ManyToOne
    @JoinColumn(name = "PackageID", nullable = false)
    private MembershipPackage packageInfo;

    // Ngày giờ thanh toán
    private LocalDateTime paymentDate = LocalDateTime.now();

    // Số tiền thanh toán
    private Double amount;

    // Phương thức thanh toán (momo, vnpay,...)
    @Nationalized
    private String paymentMethod;

    // Mã giao dịch (unique, dùng xác minh)
    @Column(unique = true)
    private String transactionID;

    // Trạng thái thanh toán ("pending", "active", "expired", "canceled", ...)
    @Column(nullable = false)
    private String status = "pending";

    // ================= THÊM 3 TRƯỜNG MỚI =================

    // Ngày bắt đầu hiệu lực gói
    @Column
    private LocalDate startDate;

    // Ngày kết thúc hiệu lực gói
    @Column
    private LocalDate endDate;

    // Ngày gia hạn tiếp theo (nếu có)
    @Column
    private LocalDate renewalDate;

    // ================= GETTER/SETTER ĐẦY ĐỦ =================

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MembershipPackage getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(MembershipPackage packageInfo) {
        this.packageInfo = packageInfo;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // --- Các trường mới ---
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }
}

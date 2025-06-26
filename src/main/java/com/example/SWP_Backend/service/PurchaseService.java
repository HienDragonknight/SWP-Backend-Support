package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.PurchaseRequest;
import com.example.SWP_Backend.entity.MembershipPackage;
import com.example.SWP_Backend.entity.Payment;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.MembershipPackageRepository;
import com.example.SWP_Backend.repository.PaymentRepository;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipPackageRepository packageRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment purchasePackage(PurchaseRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MembershipPackage membershipPackage = packageRepository.findById(request.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPackageInfo(membershipPackage);
        payment.setAmount(membershipPackage.getPrice());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionID(UUID.randomUUID().toString());
        payment.setStatus("completed");

        // ======= THÊM ĐOẠN SAU ĐỂ SET CÁC TRƯỜNG NGÀY ==========
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(membershipPackage.getDurationDays());
        LocalDate renewalDate = endDate.plusDays(1); // hoặc renewalDate = endDate

        payment.setStartDate(startDate);
        payment.setEndDate(endDate);
        payment.setRenewalDate(renewalDate);

        // ======= Cập nhật thông tin user ========
        user.setCurrentMembershipPackageId(Math.toIntExact(membershipPackage.getPackageID()));
        user.setSubscriptionEndDate(endDate);
        user.setRole("member");
        userRepository.save(user);

        return paymentRepository.save(payment);
    }

}

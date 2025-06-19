package com.example.SWP_Backend.service;

import com.example.SWP_Backend.DTO.PurchaseRequest;
import com.example.SWP_Backend.entity.*;
import com.example.SWP_Backend.repository.*;
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

        // Cập nhật thông tin gói hiện tại của người dùng
        user.setCurrentMembershipPackageId(Math.toIntExact(membershipPackage.getPackageID()));
        user.setSubscriptionEndDate(LocalDate.now().plusDays(membershipPackage.getDurationDays()));
        userRepository.save(user);

        return paymentRepository.save(payment);
    }
}

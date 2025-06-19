package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

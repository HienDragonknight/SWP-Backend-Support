package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.dto.MonthlyRevenueDTO;
import com.example.SWP_Backend.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.status='completed'")
    double getTotalRevenue();

    @Query("SELECT COUNT(DISTINCT p.user.userId) FROM Payment p WHERE p.status='completed'")
    long countDistinctSubscribers();

    @Query("""
        SELECT new com.example.SWP_Backend.dto.MonthlyRevenueDTO(
            YEAR(p.paymentDate), MONTH(p.paymentDate), SUM(p.amount)
        )
        FROM Payment p
        WHERE p.status='completed'
        GROUP BY YEAR(p.paymentDate), MONTH(p.paymentDate)
        ORDER BY YEAR(p.paymentDate), MONTH(p.paymentDate)
    """)
    List<MonthlyRevenueDTO> getMonthlyRevenue();

    @Query("""
        SELECT p.packageInfo.packageID
        FROM Payment p
        WHERE p.status='completed'
        GROUP BY p.packageInfo.packageID
        ORDER BY COUNT(p) DESC
    """)
    List<Long> findMostPopularPackageId(org.springframework.data.domain.Pageable pageable);
}

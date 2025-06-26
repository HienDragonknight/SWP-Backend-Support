package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.MonthlyRevenueDTO;
import com.example.SWP_Backend.dto.MonthlyUserDTO;
import com.example.SWP_Backend.entity.MembershipPackage;
import com.example.SWP_Backend.repository.MembershipPackageRepository;
import com.example.SWP_Backend.repository.PaymentRepository;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ReportService {

    @Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  MembershipPackageRepository membershipPackageRepository;


    public BigDecimal getTotalRevenue() {
        return BigDecimal.valueOf(paymentRepository.getTotalRevenue());
    }

    public long getTotalSubscribers() {
        return paymentRepository.countDistinctSubscribers();
    }

    public BigDecimal getAverageRevenuePerMember() {
        double total = paymentRepository.getTotalRevenue();
        long count = paymentRepository.countDistinctSubscribers();
        return count > 0 ? BigDecimal.valueOf(total / count) : BigDecimal.ZERO;
    }

    public MembershipPackage getMostPopularPackage() {
        List<Long> mostPopularIds = paymentRepository.findMostPopularPackageId(
                org.springframework.data.domain.PageRequest.of(0, 1));
        return mostPopularIds.isEmpty() ? null :
                membershipPackageRepository.findById(mostPopularIds.get(0)).orElse(null);
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return paymentRepository.getMonthlyRevenue();
    }

    public List<MonthlyUserDTO> getMonthlyUserCounts() {
        return userRepository.getMonthlyUserCounts();
    }

}

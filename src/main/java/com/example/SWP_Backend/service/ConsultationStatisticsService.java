package com.example.SWP_Backend.service;

import com.example.SWP_Backend.dto.ConsultationStatsDTO;
import com.example.SWP_Backend.dto.MonthlyConsultationDTO;
import com.example.SWP_Backend.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ConsultationStatisticsService {

    @Autowired
    private  ConsultationRepository consultationRepository;

    public ConsultationStatsDTO getStatisticsByCoachId(Long coachId) {
        Long totalMembers = consultationRepository.countDistinctMembersByCoachId(coachId);
        Long completedSessions = consultationRepository.countCompletedConsultationsByCoachId(coachId);
        Long totalSessions = consultationRepository.countTotalConsultationsByCoachId(coachId);
        LocalDateTime firstConsultation = consultationRepository.findFirstConsultationDate(coachId);

        double completionRate = totalSessions != 0 ? (completedSessions * 100.0 / totalSessions) : 0;
        long activeDays = firstConsultation != null ?
                ChronoUnit.DAYS.between(firstConsultation.toLocalDate(), LocalDate.now()) : 0;

        return new ConsultationStatsDTO(totalMembers, completedSessions, totalSessions, completionRate, activeDays);
    }

    public List<MonthlyConsultationDTO> getMonthlyStatistics(Long coachId) {
        List<Object[]> results = consultationRepository.countConsultationsByMonth(coachId);
        return results.stream().map(r -> new MonthlyConsultationDTO(
                (Integer) r[0], // year
                (Integer) r[1], // month
                (Long) r[2]     // total
        )).toList();
    }
}


package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.dto.MemberStatisticsDTO;
import com.example.SWP_Backend.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByUserId(Long userId);
    List<Consultation> findByCoachId(Long coachId);
    @Query("""
        SELECT new com.example.SWP_Backend.dto.MemberStatisticsDTO(
            c.userId,
            u.fullName,
            u.phoneNumber,
            u.email,
            CASE WHEN MAX(c.status) = 'approved' THEN 'Đang tư vấn' ELSE 'Tạm dừng' END,
            MAX(c.scheduledTime)
        )
        FROM Consultation c
        JOIN User u ON u.userId = c.userId
        WHERE c.coachId = :coachId
        GROUP BY c.userId, u.fullName, u.phoneNumber, u.email
        ORDER BY u.fullName
    """)
    List<MemberStatisticsDTO> findMembersByCoach(@Param("coachId") Long coachId);

    List<Consultation> findByUserIdIn(List<Long> userIds);
    List<Consultation> findByCoachIdAndUserIdIn(Long coachId, List<Long> userIds);

    @Query("SELECT COUNT(DISTINCT c.userId) FROM Consultation c WHERE c.coachId = :coachId")
    Long countDistinctMembersByCoachId(@Param("coachId") Long coachId);

    @Query("SELECT COUNT(c) FROM Consultation c WHERE c.coachId = :coachId AND c.status = 'completed'")
    Long countCompletedConsultationsByCoachId(@Param("coachId") Long coachId);

    @Query("SELECT COUNT(c) FROM Consultation c WHERE c.coachId = :coachId")
    Long countTotalConsultationsByCoachId(@Param("coachId") Long coachId);

    @Query("SELECT MIN(c.scheduledTime) FROM Consultation c WHERE c.coachId = :coachId")
    LocalDateTime findFirstConsultationDate(@Param("coachId") Long coachId);

    @Query("SELECT YEAR(c.scheduledTime) as year, MONTH(c.scheduledTime) as month, COUNT(c) as total " +
            "FROM Consultation c WHERE c.coachId = :coachId " +
            "GROUP BY YEAR(c.scheduledTime), MONTH(c.scheduledTime) ORDER BY year, month")
    List<Object[]> countConsultationsByMonth(@Param("coachId") Long coachId);
}

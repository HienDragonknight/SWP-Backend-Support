package com.example.SWP_Backend.repository;

import com.example.SWP_Backend.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByUserId(Long userId);
    List<Consultation> findByCoachId(Long coachId);
}

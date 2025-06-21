package com.example.SWP_Backend.repository;


import com.example.SWP_Backend.entity.SmokingStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SmokingStatusLogRepository extends JpaRepository<SmokingStatusLog, Long> {
    List<SmokingStatusLog> findByUserUserIdOrderByLogDateDesc(Long userId);
}


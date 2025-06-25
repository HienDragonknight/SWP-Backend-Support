package com.example.SWP_Backend.service;



import com.example.SWP_Backend.dto.SmokingStatusLogRequest;
import com.example.SWP_Backend.entity.SmokingStatusLog;
import com.example.SWP_Backend.entity.User;
import com.example.SWP_Backend.repository.SmokingStatusLogRepository;
import com.example.SWP_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmokingStatusLogServiceImpl implements SmokingStatusLogService {
    private final SmokingStatusLogRepository logRepository;
    private final UserRepository userRepository;

    @Autowired
    public SmokingStatusLogServiceImpl(SmokingStatusLogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SmokingStatusLog addLog(SmokingStatusLogRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SmokingStatusLog log = new SmokingStatusLog();
        log.setUser(user);
        log.setLogDate(req.getLogDate());
        log.setCigarettesPerDay(req.getCigarettesPerDay());
        log.setSmokingFrequency(req.getSmokingFrequency());
        log.setCostPerPack(req.getCostPerPack());
        log.setNotes(req.getNotes());
        return logRepository.save(log);
    }

    @Override
    public List<SmokingStatusLog> getByUser(Long userId) {
        return logRepository.findByUserUserIdOrderByLogDateDesc(userId);
    }
}


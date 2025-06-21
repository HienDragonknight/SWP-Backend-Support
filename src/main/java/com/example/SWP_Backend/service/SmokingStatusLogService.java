package com.example.SWP_Backend.service;


import com.example.SWP_Backend.DTO.SmokingStatusLogRequest;
import com.example.SWP_Backend.entity.SmokingStatusLog;

import java.util.List;

public interface SmokingStatusLogService {
    SmokingStatusLog addLog(SmokingStatusLogRequest req);
    List<SmokingStatusLog> getByUser(Long userId);
}

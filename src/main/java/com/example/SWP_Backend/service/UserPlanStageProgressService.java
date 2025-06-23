package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.UserPlanStageProgress;
import com.example.SWP_Backend.repository.UserPlanStageProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPlanStageProgressService {

    private final UserPlanStageProgressRepository progressRepository;

    public UserPlanStageProgressService(UserPlanStageProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public UserPlanStageProgress tickStage(Long userId, Integer sequenceOrder, boolean completed) {
        UserPlanStageProgress progress = progressRepository
                .findByUserIdAndSequenceOrder(userId, sequenceOrder)
                .orElse(new UserPlanStageProgress( userId, sequenceOrder, completed));
        progress.setCompleted(completed);
        return progressRepository.save(progress);
    }

    public double getUserProgressPercentage(Long userId, int totalStages) {
        long completedCount = progressRepository.findByUserIdAndCompletedTrue(userId).size();
        return totalStages == 0 ? 0 : (completedCount * 100.0 / totalStages);
    }
}

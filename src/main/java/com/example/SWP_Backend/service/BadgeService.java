package com.example.SWP_Backend.service;

import com.example.SWP_Backend.entity.UserBadge;
import com.example.SWP_Backend.entity.UserPlanStageProgress;
import com.example.SWP_Backend.repository.UserBadgeRepository;
import com.example.SWP_Backend.repository.UserPlanStageProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    private final UserBadgeRepository userBadgeRepository;
    private final UserPlanStageProgressRepository userPlanStageProgressRepository;

    public BadgeService(UserBadgeRepository userBadgeRepository,
                        UserPlanStageProgressRepository userPlanStageProgressRepository) {
        this.userBadgeRepository = userBadgeRepository;
        this.userPlanStageProgressRepository = userPlanStageProgressRepository;
    }

    public List<UserBadge> getUserBadges(Integer userId) {
        return userBadgeRepository.findByUserId(userId);
    }

    public List<UserBadge> awardBadgesIfEligible(Integer userId) {
        List<UserPlanStageProgress> progressList = userPlanStageProgressRepository.findByUserId(userId);

        long totalStages = progressList.size();
        long completedStages = progressList.stream().filter(UserPlanStageProgress::isCompleted).count();

        double percentCompleted = totalStages == 0 ? 0 : (completedStages * 100.0) / totalStages;

        // Xóa badge cũ để cấp lại đúng
        userBadgeRepository.deleteAll(getUserBadges(userId));

        // Award badge dựa trên tiến độ
        if (completedStages >= 1) {
            userBadgeRepository.save(new UserBadge(null, userId, "Bước đầu tiên", "Hoàn thành ít nhất 1 giai đoạn cai thuốc!"));
        }
        if (percentCompleted >= 50) {
            userBadgeRepository.save(new UserBadge(null, userId, "Chiến binh", "Hoàn thành ít nhất 50% tiến trình cai thuốc!"));
        }
        if (percentCompleted == 100) {
            userBadgeRepository.save(new UserBadge(null, userId, "Chuyên gia", "Hoàn thành toàn bộ tiến trình cai thuốc!"));
        }

        return getUserBadges(userId);
    }
}

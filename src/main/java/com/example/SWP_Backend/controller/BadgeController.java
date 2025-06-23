package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.entity.UserBadge;
import com.example.SWP_Backend.service.BadgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/{userId}")
    public List<UserBadge> getBadges(@PathVariable Integer userId) {
        return badgeService.getUserBadges(userId);
    }

    @PostMapping("/award/{userId}")
    public List<UserBadge> awardBadges(@PathVariable Integer userId) {
        return badgeService.awardBadgesIfEligible(userId);
    }
}

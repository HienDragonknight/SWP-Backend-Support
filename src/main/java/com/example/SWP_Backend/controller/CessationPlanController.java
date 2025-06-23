package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.DTO.CessationPlanRequest;
import com.example.SWP_Backend.DTO.CessationPlanUpdateRequest;
import com.example.SWP_Backend.entity.CessationPlan;
import com.example.SWP_Backend.service.CessationPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cessation-plans")
public class CessationPlanController {
    private final CessationPlanService planService;

    @Autowired
    public CessationPlanController(CessationPlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<CessationPlan> add(@RequestBody CessationPlanRequest req) {
        return ResponseEntity.ok(planService.addPlan(req));
    }

    @PutMapping("/{planId}")
    public ResponseEntity<CessationPlan> update(
            @PathVariable Long planId,
            @RequestBody CessationPlanUpdateRequest req
    ) {
        return ResponseEntity.ok(planService.updatePlan(planId, req));
    }
/*
* PUT http://localhost:9090/api/cessation-plans/1
Content-Type: application/json

{
  "reasonToQuit": "Vì sức khỏe",
  "cigarettesPerDay": 10,
  "notes": "Cố gắng giảm dần",
  "isActive": false
}

*
*
* */

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CessationPlan>> getActive(@PathVariable Long userId) {
        return ResponseEntity.ok(planService.getActivePlans(userId));
    }
}


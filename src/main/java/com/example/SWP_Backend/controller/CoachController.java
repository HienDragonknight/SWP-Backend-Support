package com.example.SWP_Backend.controller;


import com.example.SWP_Backend.entity.Coach;
import com.example.SWP_Backend.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping("/all")
    public ResponseEntity<List<Coach>> getAllCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable Long id) {
        Optional<Coach> coach = coachService.getCoachById(id);
        return coach.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Coach> createCoach(@RequestBody Coach coach) {
        Coach savedCoach = coachService.saveCoach(coach);
        return ResponseEntity.ok(savedCoach);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCoach(@PathVariable Long id, @RequestBody Coach updatedCoach) {
        Optional<Coach> existingCoach = coachService.getCoachById(id);
        if (existingCoach.isEmpty()) return ResponseEntity.notFound().build();

        updatedCoach.setCoachId(id);
        return ResponseEntity.ok(coachService.saveCoach(updatedCoach));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id) {
        if (!coachService.existsById(id)) return ResponseEntity.notFound().build();
        coachService.deleteCoach(id);
        return ResponseEntity.ok().build();
    }
}
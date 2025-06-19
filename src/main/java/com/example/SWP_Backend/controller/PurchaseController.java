package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.DTO.PurchaseRequest;
import com.example.SWP_Backend.entity.Payment;
import com.example.SWP_Backend.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyPackage(@RequestBody PurchaseRequest request) {
        try {
            Payment payment = purchaseService.purchasePackage(request);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

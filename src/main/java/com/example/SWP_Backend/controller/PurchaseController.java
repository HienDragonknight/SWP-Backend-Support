package com.example.SWP_Backend.controller;

import com.example.SWP_Backend.dto.PaymentResponse;
import com.example.SWP_Backend.dto.PurchaseRequest;
import com.example.SWP_Backend.entity.Payment;
import com.example.SWP_Backend.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyPackage(@RequestBody PurchaseRequest request) {
        try {
            Payment payment = purchaseService.purchasePackage(request);
            PaymentResponse response = new PaymentResponse(
                    payment.getPackageInfo().getPackageName(),
                    payment.getAmount(),
                    payment.getStartDate(),
                    payment.getEndDate(),
                    payment.getRenewalDate(),
                    payment.getStatus()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

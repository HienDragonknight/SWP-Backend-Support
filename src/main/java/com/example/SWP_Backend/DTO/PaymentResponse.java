package com.example.SWP_Backend.dto;

import java.time.LocalDate;

public class PaymentResponse {
    private String packageName;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate renewalDate;
    private String status;

    // Constructor, getter, setter
    public PaymentResponse() {}

    public PaymentResponse(String packageName, Double amount, LocalDate startDate, LocalDate endDate, LocalDate renewalDate, String status) {
        this.packageName = packageName;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.renewalDate = renewalDate;
        this.status = status;
    }

    // Getter/Setter...
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getRenewalDate() { return renewalDate; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

package com.example.SWP_Backend.dto;


public class PurchaseRequest {
    private Long userId;
    private Long packageId;
    private String paymentMethod;

    public PurchaseRequest() {
    }

    public PurchaseRequest(Long userId, Long packageId, String paymentMethod) {
        this.userId = userId;
        this.packageId = packageId;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

package com.razropay.PaymentIntegraction.entity;


import lombok.Data;

@Data

public class PaymentResponseDTO {
    private String orderId;
    private String receiptId;
    private Double amount;
    private String currency;
    private String status;
    private String keyId;  // Razorpay Key ID for frontend
    private String customerName;
    private String customerEmail;
    private String productName;
}

package com.razropay.PaymentIntegraction.entity;

import java.util.Map;

import lombok.Data;

@Data
public class PaymentCallBackDTO {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private Map<String, String> response;
}

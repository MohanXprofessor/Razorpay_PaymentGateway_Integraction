package com.razropay.PaymentIntegraction.entity;


import lombok.Data;


@Data

public class PaymentRequestDTO {
    private Double amount;
    private String currency = "INR";
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String productName;
    private String description;
	
}
package com.razropay.PaymentIntegraction.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;  // Razorpay Order ID
    
    @Column(name = "receipt_id", unique = true)
    private String receiptId;
    
    @Column(name = "amount", nullable = false)
    private Double amount;  // Amount in rupees
    
    @Column(name = "currency")
    private String currency = "INR";
    
    @Column(name = "status")
    private String status;  // CREATED, PAID, FAILED
    
    @Column(name = "payment_id")
    private String paymentId;  // Razorpay Payment ID after success
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "customer_email")
    private String customerEmail;
    
    @Column(name = "customer_phone")
    private String customerPhone;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "description")
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

package com.razropay.PaymentIntegraction.services;

import com.razorpay.RazorpayException;
import com.razropay.PaymentIntegraction.entity.PaymentCallBackDTO;
import com.razropay.PaymentIntegraction.entity.PaymentOrder;
import com.razropay.PaymentIntegraction.entity.PaymentRequestDTO;
import com.razropay.PaymentIntegraction.entity.PaymentResponseDTO;

public interface PaymentService {
	
    PaymentResponseDTO createOrder(PaymentRequestDTO paymentRequest) throws RazorpayException;
    
    PaymentOrder processPaymentCallback(PaymentCallBackDTO callbackDTO) throws Exception;
    
    PaymentOrder getOrderDetails(String orderId);
    
    PaymentOrder updateOrderStatus(String orderId, String status, String paymentId);
}
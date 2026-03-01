package com.razropay.PaymentIntegraction.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.razropay.PaymentIntegraction.entity.PaymentOrder;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder, Long> {
	 Optional<PaymentOrder> findByOrderId(String orderId);
	    Optional<PaymentOrder> findByReceiptId(String receiptId);
	    Optional<PaymentOrder> findByPaymentId(String paymentId);

}

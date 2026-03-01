package com.razropay.PaymentIntegraction.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.razropay.PaymentIntegraction.entity.PaymentCallBackDTO;
import com.razropay.PaymentIntegraction.entity.PaymentOrder;
import com.razropay.PaymentIntegraction.entity.PaymentRequestDTO;
import com.razropay.PaymentIntegraction.entity.PaymentResponseDTO;
import com.razropay.PaymentIntegraction.repo.PaymentOrderRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final PaymentOrderRepo paymentOrderRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    @Transactional
    public PaymentResponseDTO createOrder(PaymentRequestDTO paymentRequest) throws RazorpayException {

        log.info("Creating Razorpay order for amount: {}", paymentRequest.getAmount());

        RazorpayClient razorpayClient =
                new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", paymentRequest.getAmount() * 100);
        orderRequest.put("currency", paymentRequest.getCurrency());
        orderRequest.put("receipt",
                "receipt_" + UUID.randomUUID().toString().substring(0, 8));

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        log.info("Razorpay order created with ID: {}", razorpayOrder.get("id").toString());

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setOrderId(razorpayOrder.get("id"));
        paymentOrder.setReceiptId(razorpayOrder.get("receipt"));
        paymentOrder.setAmount(paymentRequest.getAmount());
        paymentOrder.setCurrency(paymentRequest.getCurrency());
        paymentOrder.setStatus("CREATED");
        paymentOrder.setCustomerName(paymentRequest.getCustomerName());
        paymentOrder.setCustomerEmail(paymentRequest.getCustomerEmail());
        paymentOrder.setCustomerPhone(paymentRequest.getCustomerPhone());
        paymentOrder.setProductName(paymentRequest.getProductName());
        paymentOrder.setDescription(paymentRequest.getDescription());

        paymentOrderRepository.save(paymentOrder);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setOrderId(razorpayOrder.get("id"));
        response.setReceiptId(razorpayOrder.get("receipt"));
        response.setAmount(paymentRequest.getAmount());
        response.setCurrency(paymentRequest.getCurrency());
        response.setStatus("CREATED");
        response.setKeyId(razorpayKeyId);
        response.setCustomerName(paymentRequest.getCustomerName());
        response.setCustomerEmail(paymentRequest.getCustomerEmail());
        response.setProductName(paymentRequest.getProductName());

        return response;
    }

    @Override
    @Transactional
    public PaymentOrder processPaymentCallback(PaymentCallBackDTO callbackDTO) throws Exception {

        log.info("Processing payment callback for order: {}",
                callbackDTO.getRazorpayOrderId());

        String orderId = callbackDTO.getRazorpayOrderId();
        String paymentId = callbackDTO.getRazorpayPaymentId();

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature",
                callbackDTO.getRazorpaySignature());

        boolean isValid =
                Utils.verifyPaymentSignature(options, razorpayKeySecret);

        PaymentOrder paymentOrder = paymentOrderRepository
                .findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with ID: " + orderId));

        if (isValid) {
            paymentOrder.setStatus("PAID");
            paymentOrder.setPaymentId(paymentId);
            log.info("Payment verified successfully for order: {}", orderId);
        } else {
            paymentOrder.setStatus("FAILED");
            log.error("Payment verification failed for order: {}", orderId);
            throw new Exception("Payment signature verification failed");
        }

        return paymentOrderRepository.save(paymentOrder);
    }

	@Override
	public PaymentOrder getOrderDetails(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentOrder updateOrderStatus(String orderId, String status, String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
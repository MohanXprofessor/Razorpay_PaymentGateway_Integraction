package com.razropay.PaymentIntegraction.controller;

import com.razropay.PaymentIntegraction.entity.PaymentRequestDTO;
import com.razropay.PaymentIntegraction.entity.PaymentResponseDTO;
import com.razropay.PaymentIntegraction.services.PaymentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PaymentController {

	@Autowired
    private final PaymentService paymentService;

    @GetMapping("/")
    public String paymentPage(Model model) {
        model.addAttribute("payment", new PaymentRequestDTO());
        return "payment";
    }

    @PostMapping("/create-order")
    public String createOrder(@ModelAttribute PaymentRequestDTO request,
                              Model model) throws Exception {

        PaymentResponseDTO response = paymentService.createOrder(request);
        model.addAttribute("order", response);

        return "checkout";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }
}

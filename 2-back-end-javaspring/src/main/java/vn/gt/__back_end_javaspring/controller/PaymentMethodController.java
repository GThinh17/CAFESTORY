package vn.gt.__back_end_javaspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.entity.PaymentMethod;
import vn.gt.__back_end_javaspring.service.PaymentMethodService;
import vn.gt.__back_end_javaspring.service.PaymentService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/paymentMethod")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService methodService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPaymentMethod() {
        List<PaymentMethod> list = this.methodService.GetAllPayMenthodService();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentMethod(@Valid @PathVariable("id") String id) {
        PaymentMethod payment = this.methodService.GetPaymentMethodService(id);
        return ResponseEntity.ok().body(payment);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPaymentMethod(@Valid @RequestBody PaymentMethod payment) {
        PaymentMethod createPayment = this.methodService.CreatePaymentMethodService(payment);
        return ResponseEntity.ok().body(createPayment);
    }

}

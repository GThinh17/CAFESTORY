package vn.gt.__back_end_javaspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.PaymentResponse;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.service.PaymentService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("")
    public ResponseEntity<?> getAllPayment() {
        List<PaymentResponse> list = this.paymentService.GetAllUserPayment();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@Valid @PathVariable("id") String id) {
        Optional<?> payment = this.paymentService.GetUserPayment(id);
        return ResponseEntity.ok().body(payment);
    }

    // @PostMapping("/")
    // public ResponseEntity<?> createPayment(@Valid @RequestBody Payment payment, )
    // {
    // Payment createPayment = this.paymentService.CreatePayment(payment);
    // return ResponseEntity.ok().body(createPayment);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> updatePayment(@Valid @PathVariable("id") String id)
    // {
    // Payment createPayment = this.paymentService.UpdatePayment(id);
    // return ResponseEntity.ok().body(createPayment);
    // }
}

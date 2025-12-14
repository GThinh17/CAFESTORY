package vn.gt.__back_end_javaspring.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;
import vn.gt.__back_end_javaspring.repository.PaymentRepository;
import vn.gt.__back_end_javaspring.service.PaymentService;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public List<Payment> GetAllUserPayment() {
        return this.paymentRepository.findAll();
    }

    public Optional<Payment> GetUserPayment(String id) {
        return this.paymentRepository.findById(id);
    }

    public Payment CreatePayment(Payment payment, int time) {
        payment.setProcessedAt(LocalDateTime.now());
        LocalDateTime endAt = payment.getProcessedAt().plusMonths(time);
        payment.setEndAt(endAt);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(payment.getAmount());
        payment.setProduction(payment.getProduction());
        return this.paymentRepository.save(payment);
    }

    public Payment UpdatePayment(String paymentId, PaymentStatus paymentStatus) {


        Payment updateStatus = this.paymentRepository.findByPaymentId(paymentId);
        updateStatus.setStatus(paymentStatus);
        return this.paymentRepository.save(updateStatus);
    }

}

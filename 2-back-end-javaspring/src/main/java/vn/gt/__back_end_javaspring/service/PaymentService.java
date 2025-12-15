package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Optional;

import vn.gt.__back_end_javaspring.DTO.PaymentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PaymentResponse;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;

public interface PaymentService {
    public List<PaymentResponse> GetAllUserPayment();

    public Optional<Payment> GetUserPayment(String payString);

    public Payment CreatePayment(Payment payment, int timeExpired);

    public Payment UpdatePayment(String paymentId, PaymentStatus paymentStatus);

}

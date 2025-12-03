package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Optional;

import vn.gt.__back_end_javaspring.entity.Payment;

public interface PaymentService {
    public List<Payment> GetAllUserPayment();

    public Optional<Payment> GetUserPayment(String payString);

    public Payment CreatePayment(Payment payment);

    public Payment UpdatePaymentSUCCESS(String paymentId);

}

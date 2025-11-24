package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Optional;

import vn.gt.__back_end_javaspring.entity.PaymentDetailed;
import vn.gt.__back_end_javaspring.entity.Embedded.PaymentDetailedKey;

public interface PaymentDetailedService {
    public List<PaymentDetailed> GetAllPaymentDetailed();

    public PaymentDetailed GetPaymentDetailed(String id);

    public PaymentDetailed CreatePaymentDetailed(PaymentDetailed key, int time);

    public PaymentDetailed UpdatePaymentDetailed(String id);
}

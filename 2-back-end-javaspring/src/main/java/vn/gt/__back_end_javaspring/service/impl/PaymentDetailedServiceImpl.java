package vn.gt.__back_end_javaspring.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.PaymentDetailed;
import vn.gt.__back_end_javaspring.repository.PaymentDetailedRepository;
import vn.gt.__back_end_javaspring.service.PaymentDetailedService;

@Service
@RequiredArgsConstructor
public class PaymentDetailedServiceImpl implements PaymentDetailedService {
    private final PaymentDetailedRepository paymentDetailedRepository;

    @Override
    public List<PaymentDetailed> GetAllPaymentDetailed() {
        return this.paymentDetailedRepository.findAll();
    }

    @Override
    public PaymentDetailed GetPaymentDetailed(String id) {
        return this.paymentDetailedRepository.findByUser_id(id);
    }

    @Override
    public PaymentDetailed CreatePaymentDetailed(PaymentDetailed key, int extraFeeExpiredMonths) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(extraFeeExpiredMonths);

        key.setStartDate(Date.valueOf(start));
        key.setEndDate(Date.valueOf(end));

        return this.paymentDetailedRepository.save(key);
    }

    @Override
    public PaymentDetailed UpdatePaymentDetailed(String id) {
        return this.paymentDetailedRepository.save(this.paymentDetailedRepository.findByUser_id(id));
    }

}

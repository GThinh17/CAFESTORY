package vn.gt.__back_end_javaspring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.PaymentMethod;
import vn.gt.__back_end_javaspring.repository.PaymentMethodRepository;
import vn.gt.__back_end_javaspring.service.PaymentMethodService;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod CreatePaymentMethodService(PaymentMethod pMethodService) {
        return this.paymentMethodRepository.save(pMethodService);
    }

    @Override
    public List<PaymentMethod> GetAllPayMenthodService() {
        return this.paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod GetPaymentMethodService(String id) {
        return this.paymentMethodRepository.findByPaymentMethodId(id);
    }
}

package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;

import com.stripe.model.Transfer;

import vn.gt.__back_end_javaspring.DTO.PayOutDTO;
import vn.gt.__back_end_javaspring.DTO.PayOutResponse;

@Service

public interface PayOutService {

        public Map<String, Object> createConnectedAccount(String email) throws StripeException;

        public String generateAccountLink(String accountId) throws StripeException;

        public Transfer transferToReviewer(
                        Long amount,
                        String reviewerStripeAccountId) throws StripeException;

        public Account getAccountStatus(String accountId) throws StripeException;

        public PayOutResponse createPayOut(PayOutDTO payOutDTO);

        public List<PayOutResponse> getAllPayOut();

        public PayOutResponse updatePayOut(PayOutDTO payOutDTO, String paString);

}

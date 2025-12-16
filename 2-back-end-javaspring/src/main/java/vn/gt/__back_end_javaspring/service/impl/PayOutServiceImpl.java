package vn.gt.__back_end_javaspring.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Transfer;

import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.TransferCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.PayOutDTO;
import vn.gt.__back_end_javaspring.DTO.PayOutResponse;
import vn.gt.__back_end_javaspring.entity.Payout;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.Wallet;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;
import vn.gt.__back_end_javaspring.mapper.PayOutMapper;
import vn.gt.__back_end_javaspring.repository.PayOutRepository;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.repository.WalletRepository;
import vn.gt.__back_end_javaspring.repository.WalletTransactionRepository;
import vn.gt.__back_end_javaspring.service.PayOutService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.service.UserService;
import vn.gt.__back_end_javaspring.service.WalletService;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

@Service
@RequiredArgsConstructor
public class PayOutServiceImpl implements PayOutService {
        private final PayOutRepository payOutRepository;
        private final PayOutMapper payOutMapper;
        private final UserService userService;
        private final ReviewerRepository reviewerService;
        private final WalletRepository walletService;
        private final WalletTransactionRepository walletTransactionService;
        @Value("${stripe.secret.key}")
        private String secretKey;

        @PostConstruct
        public void init() {
                Stripe.apiKey = secretKey;
        }

        public Map<String, Object> createConnectedAccount(String email) throws StripeException {
                User user = this.userService.handleGetUserByEmail(email);
                AccountCreateParams params = AccountCreateParams.builder()
                                .setType(AccountCreateParams.Type.EXPRESS)
                                .setEmail(email)
                                .setCapabilities(
                                                AccountCreateParams.Capabilities.builder()
                                                                .setTransfers(
                                                                                AccountCreateParams.Capabilities.Transfers
                                                                                                .builder()
                                                                                                .setRequested(true)
                                                                                                .build())
                                                                .build())
                                .build();

                Account account = Account.create(params);
                user.setVertifiedBank(account.getId());
                this.userService.createUser(user);
                return Map.of(
                                "accountId", account.getId());
        }

        /*
         * ==============================
         * 2. Link xác minh ngân hàng
         * ==============================
         */
        public String generateAccountLink(String accountId) throws StripeException {

                AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                                .setAccount(accountId)
                                .setRefreshUrl("http://localhost:3000/reauth")
                                .setReturnUrl("http://localhost:3000/success")
                                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                                .build();

                AccountLink link = AccountLink.create(params);
                return link.getUrl();
        }

        /*
         * ==============================
         * 3. TRANSFER tiền cho reviewer
         * ==============================
         */
        public Transfer transferToReviewer(
                        Long amount,
                        String reviewerStripeAccountId) throws StripeException {

                TransferCreateParams params = TransferCreateParams.builder()
                                .setAmount(amount) // đơn vị CENT
                                .setCurrency("usd")
                                .setDestination(reviewerStripeAccountId)
                                .build();

                return Transfer.create(params);
        }

        /*
         * ==============================
         * 4. Kiểm tra account
         * ==============================
         */
        public Account getAccountStatus(String accountId) throws StripeException {
                return Account.retrieve(accountId);
        }

        @Override
        public PayOutResponse createPayOut(PayOutDTO payOutDTO) {

                Reviewer reviewer = this.reviewerService.findById(payOutDTO.getReviewerId()).orElseThrow();
                Wallet wallet = this.walletService.findById(payOutDTO.getWalletId()).orElseThrow();
                WalletTransaction walletTransaction = this.walletTransactionService
                                .findById(payOutDTO.getWalletTransactionId()).orElseThrow();
                BigDecimal amount = payOutDTO.getAmount();
                String currency = payOutDTO.getCurrency();
                String status = payOutDTO.getStatus();
                String note = payOutDTO.getNote();

                Payout payout = new Payout();
                payout.setReviewer(reviewer);
                payout.setWallet(wallet);
                payout.setWalletTransaction(walletTransaction);
                payout.setAmount(payOutDTO.getAmount());
                payout.setCurrency(payOutDTO.getCurrency());
                payout.setStatus("PENDING"); // lúc mới tạo
                payout.setNote(payOutDTO.getNote());
                payout.setRequestedAt(LocalDateTime.now());
                this.payOutRepository.save(payout);

                PayOutResponse payOutResponse = this.payOutMapper.toResponse(payout);
                return payOutResponse;
        }

        @Override
        public List<PayOutResponse> getAllPayOut() {
                List<Payout> payouts = payOutRepository.findAll();
                List<PayOutResponse> payOutResponses = this.payOutMapper.toResponseList(payouts);
                return payOutResponses;
        }

        @Override
        public PayOutResponse updatePayOut(PayOutDTO payOutDTO, String payString) {

                Payout payout = payOutRepository.findById(payString)
                                .orElseThrow(() -> new RuntimeException("Payout not found"));

                // Update status (nếu có)
                if (payOutDTO.getStatus() != null) {
                        payout.setStatus(payOutDTO.getStatus());
                }

                // Update note (nếu có)
                if (payOutDTO.getNote() != null) {
                        payout.setNote(payOutDTO.getNote());
                }

                // Optional: update time
                payout.setRequestedAt(LocalDateTime.now());

                Payout updated = payOutRepository.save(payout);

                return payOutMapper.toResponse(updated);

        }
}

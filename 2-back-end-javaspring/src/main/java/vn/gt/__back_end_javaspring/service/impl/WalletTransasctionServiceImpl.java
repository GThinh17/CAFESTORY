package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.Wallet;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.exception.WalletNotFound;
import vn.gt.__back_end_javaspring.exception.WalletTransactionNotFound;
import vn.gt.__back_end_javaspring.mapper.WalletTransactionMapper;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.WalletRepository;
import vn.gt.__back_end_javaspring.repository.WalletTransactionRepository;
import vn.gt.__back_end_javaspring.service.WalletService;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletTransasctionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletService walletService;
    private final WalletTransactionMapper walletTransactionMapper;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;


    @Override
    public WalletTransactionResponse create(WalletTransactionCreateDTO dto) {

        Wallet wallet = walletRepository.findById(dto.getWalletId())
                .orElseThrow(()-> new WalletNotFound("wallet not found"));

        BigDecimal amount = dto.getAmount();

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new  IllegalArgumentException("AMount must be greater than 0");
        }
        TransactionType transactionType = dto.getTransactionType();

        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal balanceAfter;

        if(transactionType == TransactionType.WITHDRAW) {
            balanceAfter = wallet.getBalance().subtract(amount);
            if(balanceAfter.compareTo(BigDecimal.ZERO) <= 0) {
                throw new  IllegalArgumentException("Insufficient funds");
            }
        } else{
            balanceAfter = balanceBefore.add(amount);
        }

        wallet.setBalance(balanceAfter);
        walletRepository.save(wallet);

        WalletTransaction tx = new WalletTransaction();
        tx.setWallet(wallet);
        tx.setAmount(amount);
        tx.setType(transactionType);
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setBalanceBefore(balanceBefore);
        tx.setBalanceAfter(balanceAfter);

        WalletTransaction saved = walletTransactionRepository.save(tx);
        return walletTransactionMapper.toResponse(saved);
    }

    @Override
    public WalletTransactionResponse getById(String transactionId) {
        WalletTransaction walletTransaction = walletTransactionRepository.findById(transactionId)
                .orElseThrow(()-> new WalletTransactionNotFound("wallet not found"));
        return walletTransactionMapper.toResponse(walletTransaction);
    }

    @Override
    public List<WalletTransactionResponse> getByWalletId(String walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()-> new WalletNotFound("wallet not found"));

        List<WalletTransaction> walletTransactions = walletTransactionRepository
                .findByWalletId(walletId);

        return walletTransactionMapper.toResponseList(walletTransactions);
    }

    @Override
    public List<WalletTransactionResponse> getByUserId(String userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(()->new UserNotFoundException("User not found"));

       Wallet wallet = walletRepository.findWalletByUser_Id(user.getId());
       List<WalletTransaction> walletTransaction = walletTransactionRepository
               .findByWalletId(wallet.getId());

       return walletTransactionMapper.toResponseList(walletTransaction);

    }

    @Override
    public List<WalletTransactionResponse> getByWalletIdAndType(String walletId, TransactionType type) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()-> new WalletNotFound("wallet not found"));

        List<WalletTransaction> walletTransactions = walletTransactionRepository
                .findByWalletIdAndType(walletId, type);
        return walletTransactionMapper.toResponseList(walletTransactions);
    }

    @Override
    public List<WalletTransactionResponse> getByWalletIdAndStatus(String walletId, TransactionStatus status) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()-> new WalletNotFound("wallet not found"));

        List<WalletTransaction> walletTransactions = walletTransactionRepository
                .findByWalletIdAndStatus(walletId, status);
        return walletTransactionMapper.toResponseList(walletTransactions);
    }
}

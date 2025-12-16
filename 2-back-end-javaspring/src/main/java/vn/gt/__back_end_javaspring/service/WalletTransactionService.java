package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;

import java.util.List;

public interface WalletTransactionService {

    WalletTransactionResponse create(WalletTransactionCreateDTO dto);

    WalletTransactionResponse getById(String transactionId);

    List<WalletTransactionResponse> getByWalletId(String walletId);

    List<WalletTransactionResponse> getByUserId(String userId);

    List<WalletTransactionResponse> getByWalletIdAndType(String walletId, TransactionType type);

    List<WalletTransactionResponse> getByWalletIdAndStatus(String walletId, TransactionStatus status);

    void updateStatuc(String walletTransactionId, TransactionStatus transactionType);

}

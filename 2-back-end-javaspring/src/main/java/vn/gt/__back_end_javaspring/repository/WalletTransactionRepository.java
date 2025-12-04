package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
    List<WalletTransaction> findByWalletId(String walletId);

    List<WalletTransaction> findByWalletIdAndType(String walletId, TransactionType type);

    List<WalletTransaction> findByWalletIdAndStatus(String walletId, TransactionStatus status);
}

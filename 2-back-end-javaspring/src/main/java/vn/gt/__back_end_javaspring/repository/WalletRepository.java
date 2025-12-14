package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.entity.Wallet;

import java.util.Optional;

public interface WalletRepository  extends JpaRepository<Wallet, String> {
    Wallet findWalletByUser_Id(String userId);
    Boolean existsByUser_Id(String userId);
   Optional<Wallet> findByIdAndIsDeletedIsFalse(String id);
   Boolean existsByUser_IdAndIsDeletedIsFalse(String userId);
}

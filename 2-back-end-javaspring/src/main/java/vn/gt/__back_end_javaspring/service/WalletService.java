package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.WalletCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    WalletResponse createWallet(WalletCreateDTO wallet);

    WalletResponse findById(String walletId);
    WalletResponse findByUserId(String userId);

    WalletResponse updateWallet(WalletUpdateDTO wallet, String walletId);

    void softDelete(String walletId);
    void restore(String walletId);

    boolean existsByUserId(String userId);

//    WalletResponse deposit(String walletId, BigDecimal amount);
//    WalletResponse withdraw(String walletId, BigDecimal amount);

}

package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.WalletCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletUpdateDTO;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.Wallet;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;
import vn.gt.__back_end_javaspring.enums.TransactionType;
import vn.gt.__back_end_javaspring.exception.UserAlreadyhaveWallet;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.exception.WalletNotFound;
import vn.gt.__back_end_javaspring.mapper.WalletMapper;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.WalletRepository;
import vn.gt.__back_end_javaspring.service.WalletService;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserRepository userRepository;


    @Override
    public WalletResponse createWallet(WalletCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(walletRepository.existsByUser_Id(dto.getUserId())) {
            throw new UserAlreadyhaveWallet("User already have wallet");
        }

        Wallet wallet = walletMapper.toModel(dto);
        wallet.setUser(user);

        Wallet saved =  walletRepository.save(wallet);
        return walletMapper.toResponse(saved);
    }

    @Override
    public WalletResponse findById(String walletId) {
        Wallet wallet = walletRepository.findByIdAndIsDeletedIsFalse(walletId)
                .orElseThrow(() -> new WalletNotFound("Wallet not found"));

        return walletMapper.toResponse(wallet);
    }

    @Override
    public WalletResponse updateWallet(WalletUpdateDTO updateDTO, String id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(()-> new WalletNotFound("Wallet not found"));

        walletMapper.updateEntity(updateDTO, wallet);

        Wallet saved = walletRepository.save(wallet);
        return walletMapper.toResponse(saved);
    }

    @Override
    public void softDelete(String id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(()-> new WalletNotFound("Wallet not found"));

        wallet.setIsDeleted(true);
        walletRepository.save(wallet);
    }

    @Override
    public void restore(String walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()-> new WalletNotFound("Wallet not found"));

        wallet.setIsDeleted(false);

        walletRepository.save(wallet);
    }

    @Override
    public boolean existsByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return walletRepository.existsByUser_IdAndIsDeletedIsFalse(userId);
    }

//    @Override
//    public WalletResponse deposit(String walletId, BigDecimal amount) {
//        if(amount ==  null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//
//
//        Wallet wallet = walletRepository.findById(walletId)
//                .orElseThrow(()-> new WalletNotFound("Wallet not found"));
//
//        //WalletCreateDTo
//        WalletTransactionCreateDTO walletTransactionCreateDTO = new WalletTransactionCreateDTO();
//        walletTransactionCreateDTO.setWalletId(walletId);
//        walletTransactionCreateDTO.setAmount(amount);
//        walletTransactionCreateDTO.setTransactionType(TransactionType.DEPOSIT);
//        walletTransactionService.create(walletTransactionCreateDTO);
//
//
//        wallet.setBalance(wallet.getBalance().add(amount));
//        walletRepository.save(wallet);
//        return walletMapper.toResponse(wallet);
//    }
//
//    @Override
//    public WalletResponse withdraw(String walletId, BigDecimal amount) {
//        if(amount ==  null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//        Wallet wallet = walletRepository.findById(walletId)
//                .orElseThrow(()-> new WalletNotFound("Wallet not found"));
//
//        if(wallet.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Insufficient funds");
//        }
//
//        wallet.setBalance(wallet.getBalance().subtract(amount));
//        walletRepository.save(wallet);
//        return walletMapper.toResponse(wallet);
//    }



    @Override
    public WalletResponse findByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        Wallet wallet = walletRepository.findWalletByUser_Id(userId);


        return walletMapper.toResponse(wallet);

    }
}

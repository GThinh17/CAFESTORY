package vn.gt.__back_end_javaspring.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.Wallet;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T07:31:03+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class WalletTransactionMapperImpl implements WalletTransactionMapper {

    @Override
    public WalletTransaction toEntity(WalletTransactionCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        WalletTransaction walletTransaction = new WalletTransaction();

        walletTransaction.setAmount( dto.getAmount() );

        return walletTransaction;
    }

    @Override
    public List<WalletTransactionResponse> toResponseList(List<WalletTransaction> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<WalletTransactionResponse> list = new ArrayList<WalletTransactionResponse>( dtos.size() );
        for ( WalletTransaction walletTransaction : dtos ) {
            list.add( toResponse( walletTransaction ) );
        }

        return list;
    }

    @Override
    public WalletTransactionResponse toResponse(WalletTransaction walletTransaction) {
        if ( walletTransaction == null ) {
            return null;
        }

        WalletTransactionResponse.WalletTransactionResponseBuilder walletTransactionResponse = WalletTransactionResponse.builder();

        walletTransactionResponse.walletTransactionId( walletTransaction.getId() );
        walletTransactionResponse.walletId( walletTransactionWalletId( walletTransaction ) );
        walletTransactionResponse.walletBalance( walletTransactionWalletBalance( walletTransaction ) );
        walletTransactionResponse.walletUserId( walletTransactionWalletUserId( walletTransaction ) );
        if ( walletTransaction.getType() != null ) {
            walletTransactionResponse.type( walletTransaction.getType().name() );
        }
        if ( walletTransaction.getStatus() != null ) {
            walletTransactionResponse.status( walletTransaction.getStatus().name() );
        }
        walletTransactionResponse.amount( walletTransaction.getAmount() );
        walletTransactionResponse.balanceAfter( walletTransaction.getBalanceAfter() );
        walletTransactionResponse.balanceBefore( walletTransaction.getBalanceBefore() );
        walletTransactionResponse.createdAt( walletTransaction.getCreatedAt() );
        walletTransactionResponse.updatedAt( walletTransaction.getUpdatedAt() );

        return walletTransactionResponse.build();
    }

    private String walletTransactionWalletId(WalletTransaction walletTransaction) {
        Wallet wallet = walletTransaction.getWallet();
        if ( wallet == null ) {
            return null;
        }
        return wallet.getId();
    }

    private BigDecimal walletTransactionWalletBalance(WalletTransaction walletTransaction) {
        Wallet wallet = walletTransaction.getWallet();
        if ( wallet == null ) {
            return null;
        }
        return wallet.getBalance();
    }

    private String walletTransactionWalletUserId(WalletTransaction walletTransaction) {
        Wallet wallet = walletTransaction.getWallet();
        if ( wallet == null ) {
            return null;
        }
        User user = wallet.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}

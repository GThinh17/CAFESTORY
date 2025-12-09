package vn.gt.__back_end_javaspring.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.WalletCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletUpdateDTO;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.Wallet;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T16:04:00+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public Wallet toModel(WalletCreateDTO walletCreateDTO) {
        if ( walletCreateDTO == null ) {
            return null;
        }

        Wallet wallet = new Wallet();

        return wallet;
    }

    @Override
    public WalletResponse toResponse(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletResponse.WalletResponseBuilder walletResponse = WalletResponse.builder();

        walletResponse.walletId( wallet.getId() );
        walletResponse.userId( walletUserId( wallet ) );
        walletResponse.userName( walletUserFullName( wallet ) );
        walletResponse.userAvatar( walletUserAvatar( wallet ) );
        walletResponse.balance( wallet.getBalance() );
        walletResponse.createdAt( wallet.getCreatedAt() );
        walletResponse.currency( wallet.getCurrency() );
        walletResponse.isDeleted( wallet.getIsDeleted() );
        walletResponse.updatedAt( wallet.getUpdatedAt() );

        return walletResponse.build();
    }

    @Override
    public void updateEntity(WalletUpdateDTO walletUpdateDTO, Wallet wallet) {
        if ( walletUpdateDTO == null ) {
            return;
        }

        if ( walletUpdateDTO.getBalance() != null ) {
            wallet.setBalance( walletUpdateDTO.getBalance() );
        }
        if ( walletUpdateDTO.getIsDeleted() != null ) {
            wallet.setIsDeleted( walletUpdateDTO.getIsDeleted() );
        }
    }

    private String walletUserId(Wallet wallet) {
        User user = wallet.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String walletUserFullName(Wallet wallet) {
        User user = wallet.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String walletUserAvatar(Wallet wallet) {
        User user = wallet.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }
}

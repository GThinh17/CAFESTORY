package vn.gt.__back_end_javaspring.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletTransactionMapper {

    WalletTransaction toEntity(WalletTransactionCreateDTO dto);

    List<WalletTransactionResponse> toResponseList(List<WalletTransaction> dtos);

    @Mapping(source = "id", target = "walletTransactionId")
    @Mapping(source = "wallet.id", target = "walletId")
    @Mapping(source = "wallet.balance", target = "walletBalance")
    @Mapping(source = "wallet.user.id", target = "walletUserId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    WalletTransactionResponse toResponse(WalletTransaction walletTransaction);

}

package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.WalletCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Wallet;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    Wallet toModel(WalletCreateDTO walletCreateDTO);

    @Mapping(source = "id", target = "walletId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.avatar", target = "userAvatar")
    WalletResponse toResponse(Wallet wallet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(WalletUpdateDTO walletUpdateDTO, @MappingTarget Wallet wallet);
}

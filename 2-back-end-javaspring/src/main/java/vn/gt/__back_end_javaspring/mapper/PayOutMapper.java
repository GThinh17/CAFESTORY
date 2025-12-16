package vn.gt.__back_end_javaspring.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.gt.__back_end_javaspring.DTO.PayOutDTO;
import vn.gt.__back_end_javaspring.DTO.PayOutResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.entity.Payout;
import vn.gt.__back_end_javaspring.entity.Reviewer;

@Mapper(componentModel = "spring")
public interface PayOutMapper {
    Payout toEntity(PayOutDTO payOutDTO);

    @Mapping(target = "reviewerId", source = "reviewer.id")
    @Mapping(target = "walletId", source = "wallet.id")
    @Mapping(target = "userId", source = "wallet.user.id")
    @Mapping(target = "walletTransactionId", source = "walletTransaction.id")
    @Mapping(target = "balancedBefore", source = "walletTransaction.balanceBefore")
    @Mapping(target = "balancedAfter", source = "walletTransaction.balanceAfter")
    @Mapping(target = "walletTransactionStatus", source = "walletTransaction.status")
    PayOutResponse toResponse(Payout payout);

    List<PayOutResponse> toResponseList(List<Payout> payouts);
}

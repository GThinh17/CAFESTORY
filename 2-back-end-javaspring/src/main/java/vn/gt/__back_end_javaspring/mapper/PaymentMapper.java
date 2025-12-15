package vn.gt.__back_end_javaspring.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.PaymentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PaymentResponse;
import vn.gt.__back_end_javaspring.DTO.TagResponse;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.entity.Tag;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentCreateDTO dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productionId", source = "production.productionId")
    @Mapping(target = "total", source = "production.total")

    PaymentResponse toResponse(Payment entity);

    List<PaymentResponse> toResponseList(List<Payment> tags);
}

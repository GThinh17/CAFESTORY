package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T16:55:23+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class CafeOwnerMapperImpl implements CafeOwnerMapper {

    @Override
    public CafeOwner toEntity(CafeOwnerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CafeOwner cafeOwner = new CafeOwner();

        return cafeOwner;
    }

    @Override
    public CafeOwnerResponse toResponse(CafeOwner entity) {
        if ( entity == null ) {
            return null;
        }

        CafeOwnerResponse.CafeOwnerResponseBuilder cafeOwnerResponse = CafeOwnerResponse.builder();

        cafeOwnerResponse.userId( entityUserId( entity ) );
        cafeOwnerResponse.userName( entityUserFullName( entity ) );
        cafeOwnerResponse.userAvatar( entityUserAvatar( entity ) );
        cafeOwnerResponse.id( entity.getId() );
        cafeOwnerResponse.businessName( entity.getBusinessName() );
        cafeOwnerResponse.totalReview( entity.getTotalReview() );
        cafeOwnerResponse.averageRating( entity.getAverageRating() );
        cafeOwnerResponse.contactEmail( entity.getContactEmail() );
        cafeOwnerResponse.contactPhone( entity.getContactPhone() );
        cafeOwnerResponse.expiredAt( entity.getExpiredAt() );
        cafeOwnerResponse.joinAt( entity.getJoinAt() );
        cafeOwnerResponse.createdAt( entity.getCreatedAt() );

        return cafeOwnerResponse.build();
    }

    @Override
    public List<CafeOwnerResponse> toResponseList(List<CafeOwner> entity) {
        if ( entity == null ) {
            return null;
        }

        List<CafeOwnerResponse> list = new ArrayList<CafeOwnerResponse>( entity.size() );
        for ( CafeOwner cafeOwner : entity ) {
            list.add( toResponse( cafeOwner ) );
        }

        return list;
    }

    @Override
    public void updateEntity(CafeOwnerUpdateDTO dto, CafeOwner entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBusinessName() != null ) {
            entity.setBusinessName( dto.getBusinessName() );
        }
        if ( dto.getTotalReview() != null ) {
            entity.setTotalReview( dto.getTotalReview() );
        }
        if ( dto.getAverageRating() != null ) {
            entity.setAverageRating( dto.getAverageRating() );
        }
        if ( dto.getContactEmail() != null ) {
            entity.setContactEmail( dto.getContactEmail() );
        }
        if ( dto.getContactPhone() != null ) {
            entity.setContactPhone( dto.getContactPhone() );
        }
    }

    private String entityUserId(CafeOwner cafeOwner) {
        User user = cafeOwner.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String entityUserFullName(CafeOwner cafeOwner) {
        User user = cafeOwner.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String entityUserAvatar(CafeOwner cafeOwner) {
        User user = cafeOwner.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }
}

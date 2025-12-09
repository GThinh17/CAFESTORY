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
    date = "2025-12-09T16:11:28+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
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
        cafeOwnerResponse.averageRating( entity.getAverageRating() );
        cafeOwnerResponse.businessName( entity.getBusinessName() );
        cafeOwnerResponse.contactEmail( entity.getContactEmail() );
        cafeOwnerResponse.contactPhone( entity.getContactPhone() );
        cafeOwnerResponse.createdAt( entity.getCreatedAt() );
        cafeOwnerResponse.expiredAt( entity.getExpiredAt() );
        cafeOwnerResponse.id( entity.getId() );
        cafeOwnerResponse.joinAt( entity.getJoinAt() );
        cafeOwnerResponse.totalReview( entity.getTotalReview() );

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

        if ( dto.getAverageRating() != null ) {
            entity.setAverageRating( dto.getAverageRating() );
        }
        if ( dto.getBusinessName() != null ) {
            entity.setBusinessName( dto.getBusinessName() );
        }
        if ( dto.getContactEmail() != null ) {
            entity.setContactEmail( dto.getContactEmail() );
        }
        if ( dto.getContactPhone() != null ) {
            entity.setContactPhone( dto.getContactPhone() );
        }
        if ( dto.getTotalReview() != null ) {
            entity.setTotalReview( dto.getTotalReview() );
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

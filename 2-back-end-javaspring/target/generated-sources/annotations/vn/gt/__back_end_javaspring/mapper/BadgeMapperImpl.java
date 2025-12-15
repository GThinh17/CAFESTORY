package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.BadgeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.DTO.BadgeUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Badge;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T05:07:34+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class BadgeMapperImpl implements BadgeMapper {

    @Override
    public Badge toEntity(BadgeCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Badge badge = new Badge();

        badge.setBadgeName( dto.getBadgeName() );
        badge.setDescription( dto.getDescription() );
        badge.setIconUrl( dto.getIconUrl() );
        badge.setCriteriaType( dto.getCriteriaType() );
        badge.setCriteriaValue( dto.getCriteriaValue() );

        return badge;
    }

    @Override
    public BadgeResponse toResponse(Badge entity) {
        if ( entity == null ) {
            return null;
        }

        BadgeResponse.BadgeResponseBuilder badgeResponse = BadgeResponse.builder();

        badgeResponse.badgeId( entity.getId() );
        badgeResponse.badgeName( entity.getBadgeName() );
        badgeResponse.description( entity.getDescription() );
        badgeResponse.iconUrl( entity.getIconUrl() );
        badgeResponse.isDeleted( entity.getIsDeleted() );
        badgeResponse.criteriaType( entity.getCriteriaType() );
        badgeResponse.criteriaValue( entity.getCriteriaValue() );
        badgeResponse.createdAt( entity.getCreatedAt() );
        badgeResponse.updatedAt( entity.getUpdatedAt() );

        return badgeResponse.build();
    }

    @Override
    public List<BadgeResponse> toResponses(List<Badge> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BadgeResponse> list = new ArrayList<BadgeResponse>( entities.size() );
        for ( Badge badge : entities ) {
            list.add( toResponse( badge ) );
        }

        return list;
    }

    @Override
    public void updateEntity(BadgeUpdateDTO dto, Badge entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBadgeName() != null ) {
            entity.setBadgeName( dto.getBadgeName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getIconUrl() != null ) {
            entity.setIconUrl( dto.getIconUrl() );
        }
        if ( dto.getIsDeleted() != null ) {
            entity.setIsDeleted( dto.getIsDeleted() );
        }
        if ( dto.getCriteriaValue() != null ) {
            entity.setCriteriaValue( dto.getCriteriaValue() );
        }
    }
}

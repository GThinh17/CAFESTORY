package vn.gt.__back_end_javaspring.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T16:45:37+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ReviewerMapperImpl implements ReviewerMapper {

    @Override
    public Reviewer toModel(ReviewerCreateDTO reviewerCreateDTO) {
        if ( reviewerCreateDTO == null ) {
            return null;
        }

        Reviewer reviewer = new Reviewer();

        return reviewer;
    }

    @Override
    public ReviewerResponse toResponse(Reviewer reviewer) {
        if ( reviewer == null ) {
            return null;
        }

        ReviewerResponse reviewerResponse = new ReviewerResponse();

        reviewerResponse.setUserId( reviewerUserId( reviewer ) );
        reviewerResponse.setUserName( reviewerUserFullName( reviewer ) );
        reviewerResponse.setUserAvatarUrl( reviewerUserAvatar( reviewer ) );
        reviewerResponse.setUserEmail( reviewerUserEmail( reviewer ) );
        reviewerResponse.setId( reviewer.getId() );
        reviewerResponse.setBio( reviewer.getBio() );
        reviewerResponse.setTotalScore( reviewer.getTotalScore() );
        reviewerResponse.setJoinAt( reviewer.getJoinAt() );
        reviewerResponse.setExpiredAt( reviewer.getExpiredAt() );
        reviewerResponse.setIsDeleted( reviewer.getIsDeleted() );
        if ( reviewer.getStatus() != null ) {
            reviewerResponse.setStatus( reviewer.getStatus().name() );
        }

        return reviewerResponse;
    }

    @Override
    public void updateEntity(ReviewerUpdateDTO dto, Reviewer entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getBio() != null ) {
            entity.setBio( dto.getBio() );
        }
        if ( dto.getTotalScore() != null ) {
            entity.setTotalScore( dto.getTotalScore() );
        }
    }

    private String reviewerUserId(Reviewer reviewer) {
        User user = reviewer.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String reviewerUserFullName(Reviewer reviewer) {
        User user = reviewer.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String reviewerUserAvatar(Reviewer reviewer) {
        User user = reviewer.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }

    private String reviewerUserEmail(Reviewer reviewer) {
        User user = reviewer.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getEmail();
    }
}

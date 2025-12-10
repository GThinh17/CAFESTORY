package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T16:55:23+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
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

        ReviewerResponse.ReviewerResponseBuilder reviewerResponse = ReviewerResponse.builder();

        reviewerResponse.userId( reviewerUserId( reviewer ) );
        reviewerResponse.userName( reviewerUserFullName( reviewer ) );
        reviewerResponse.userAvatarUrl( reviewerUserAvatar( reviewer ) );
        reviewerResponse.userEmail( reviewerUserEmail( reviewer ) );
        reviewerResponse.id( reviewer.getId() );
        reviewerResponse.bio( reviewer.getBio() );
        reviewerResponse.followerCount( reviewer.getFollowerCount() );
        reviewerResponse.totalScore( reviewer.getTotalScore() );
        reviewerResponse.joinAt( reviewer.getJoinAt() );
        reviewerResponse.expiredAt( reviewer.getExpiredAt() );
        reviewerResponse.isDeleted( reviewer.getIsDeleted() );
        if ( reviewer.getStatus() != null ) {
            reviewerResponse.status( reviewer.getStatus().name() );
        }

        return reviewerResponse.build();
    }

    @Override
    public List<ReviewerResponse> toResponseList(List<Reviewer> reviewers) {
        if ( reviewers == null ) {
            return null;
        }

        List<ReviewerResponse> list = new ArrayList<ReviewerResponse>( reviewers.size() );
        for ( Reviewer reviewer : reviewers ) {
            list.add( toResponse( reviewer ) );
        }

        return list;
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

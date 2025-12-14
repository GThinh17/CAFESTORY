package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.Page;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T22:19:10+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class PageMapperImpl implements PageMapper {

    @Override
    public Page toModel(PageCreateDTO request) {
        if ( request == null ) {
            return null;
        }

        Page page = new Page();

        page.setPageName( request.getPageName() );
        page.setSlug( request.getSlug() );
        page.setDescription( request.getDescription() );
        page.setAvatarUrl( request.getAvatarUrl() );
        page.setCoverUrl( request.getCoverUrl() );
        page.setContactPhone( request.getContactPhone() );
        page.setContactEmail( request.getContactEmail() );
        page.setOpenHours( map( request.getOpenHours() ) );

        return page;
    }

    @Override
    public PageResponse toResponse(Page page) {
        if ( page == null ) {
            return null;
        }

        PageResponse.PageResponseBuilder pageResponse = PageResponse.builder();

        pageResponse.pageId( page.getId() );
        pageResponse.businessName( pageCafeOwnerBusinessName( page ) );
        pageResponse.cafeOwnerId( pageCafeOwnerId( page ) );
        pageResponse.pageName( page.getPageName() );
        pageResponse.postCount( page.getPostCount() );
        pageResponse.slug( page.getSlug() );
        pageResponse.followingCount( page.getFollowingCount() );
        pageResponse.description( page.getDescription() );
        pageResponse.avatarUrl( page.getAvatarUrl() );
        pageResponse.coverUrl( page.getCoverUrl() );
        pageResponse.contactPhone( page.getContactPhone() );
        pageResponse.contactEmail( page.getContactEmail() );
        pageResponse.isVerified( page.getIsVerified() );
        pageResponse.verifiedAt( page.getVerifiedAt() );
        pageResponse.openHours( page.getOpenHours() );
        pageResponse.createdAt( page.getCreatedAt() );
        pageResponse.updatedAt( page.getUpdatedAt() );

        return pageResponse.build();
    }

    @Override
    public List<PageResponse> toResponse(List<Page> pages) {
        if ( pages == null ) {
            return null;
        }

        List<PageResponse> list = new ArrayList<PageResponse>( pages.size() );
        for ( Page page : pages ) {
            list.add( toResponse( page ) );
        }

        return list;
    }

    @Override
    public void updateEntity(Page page, PageUpdateDTO request) {
        if ( request == null ) {
            return;
        }

        if ( request.getPageName() != null ) {
            page.setPageName( request.getPageName() );
        }
        if ( request.getDescription() != null ) {
            page.setDescription( request.getDescription() );
        }
        if ( request.getAvatarUrl() != null ) {
            page.setAvatarUrl( request.getAvatarUrl() );
        }
        if ( request.getCoverUrl() != null ) {
            page.setCoverUrl( request.getCoverUrl() );
        }
        if ( request.getContactPhone() != null ) {
            page.setContactPhone( request.getContactPhone() );
        }
        if ( request.getContactEmail() != null ) {
            page.setContactEmail( request.getContactEmail() );
        }
        if ( request.getOpenHours() != null ) {
            page.setOpenHours( map( request.getOpenHours() ) );
        }
    }

    private String pageCafeOwnerBusinessName(Page page) {
        CafeOwner cafeOwner = page.getCafeOwner();
        if ( cafeOwner == null ) {
            return null;
        }
        return cafeOwner.getBusinessName();
    }

    private String pageCafeOwnerId(Page page) {
        CafeOwner cafeOwner = page.getCafeOwner();
        if ( cafeOwner == null ) {
            return null;
        }
        return cafeOwner.getId();
    }
}

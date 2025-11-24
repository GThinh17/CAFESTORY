package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.Visibility;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {

    private String id;
    private String caption;

    private List<String> mediaUrls;

    private Visibility visibility;

    private Boolean isPin;

    private Boolean allowComment;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String locationId;
    private String locationName;
    private String addressline;
    private String longitude;
    private String latitude;

    private String userId;
    private String userFullName;
    private String userAvatar;

    private String pageId;

    private Long commentCount;
    private Long likeCount;
    private Long shareCount;
}

package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    private String pageId;
    private String cafeOwnerId;
    private String businessName;
    private String pageName;
    private Long postCount;
    private String slug;
    private String location;
    private Long followersCount;
    private Long followingCount;
    private String description;
    private String avatarUrl;
    private String coverUrl;
    private String contactPhone;
    private String contactEmail;
    private Boolean isVerified;
    private LocalDateTime verifiedAt;
    private String openHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

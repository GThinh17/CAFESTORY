package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Location;
import vn.gt.__back_end_javaspring.entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareReponse {
    private String shareId;

    private String userId;

    private String userFullName;

    private String userAvatarUrl;

    private String blogId;

    private String blogCaption;

    private LocalDateTime blogCreatedAt;


    private Boolean blogIsDeleted;

    private String locationId;

    private String locationName;

    private String addressLine;

    private String ward;

    private String province;

    private String longitude;

    private String latitude;

    private String caption;

    private LocalDateTime createdAt;

    private Boolean isDeleted;
}

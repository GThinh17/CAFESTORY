
package vn.gt.__back_end_javaspring.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {
    private String id;

    private String content;

    private String imageUrl;

    private int likeCount ;

    private int shareCount;

    private boolean status;

    private LocalDateTime createAt;

    //Information about writer
    //Just get nessessary information about user in blog to show
    private String userId;

    private String userName;

    private String userFullName;

    private String userAvatar;

    private int commentCount;

}

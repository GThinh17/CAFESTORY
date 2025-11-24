package vn.gt.__back_end_javaspring.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) //ai null, khong co liet ke
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //neu khong set, dua ve null het
public class CommentResponse {
    private String commentId;

    private String blogId;
    private String userId;
    private String userFullName;
    private String userAvatar;

    private String parentCommentId;

    private String content;

    private String commentImageId;
    private String commentImageUrl;

    private Long likesCount;
    private Long replyCount;

    private Boolean isEdited;
    private Boolean isDeleted;
    private Boolean isPin;

    private Long cooldownSecond;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private List<CommentResponse> replies = new ArrayList<>();

}


package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.FollowType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowCreateDTO {
    @NotBlank(message = "UserId is Required")
    private String followerId;// User

    @NotNull(message = "Type is required")
    private FollowType followType; // User /page


    private String followedUserId;

    private String followedPageId;
   
}
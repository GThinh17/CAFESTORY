package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Location;
import vn.gt.__back_end_javaspring.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareCreateDTO {
    @NotBlank(message = "User is required")
    private String userId;

    @NotBlank(message = "Blog is required")
    private String blogId;

    private String locationId;

    private String caption;

}

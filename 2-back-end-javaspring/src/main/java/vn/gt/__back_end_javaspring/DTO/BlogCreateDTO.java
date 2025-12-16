package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.Visibility;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogCreateDTO {

    @NotBlank(message = "Caption must not be empty")
    @Size(max = 10000, message = "Caption must be at most 10000 characters")
    private String caption;

    private List<String> mediaUrls;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;

    private String locationId;

    private String pageId;

    @NotNull(message = "userId is required")
    private String userId;



}

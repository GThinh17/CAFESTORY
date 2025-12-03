package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageAlbumCreateDTO {
    @NotNull(message = "pageId is required")
    private String pageId;
    @NotNull(message = "title is required")
    private String title;
    @NotNull(message = "visibility is required")
    private String visibility;
}

package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.PageImage;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageAlbumResponse {
    private String pageAlbumId;
    private String pageId;
    private String title;
    private String visibility;
    private List<PageImageResponseDTO> images;
    private Integer totalPhoto;
    private Boolean isDeleted;
}

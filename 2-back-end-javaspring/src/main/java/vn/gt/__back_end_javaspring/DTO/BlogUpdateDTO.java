package vn.gt.__back_end_javaspring.DTO;

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
public class BlogUpdateDTO {

    @Size(max = 10000, message = "Caption must be at most 10000 characters")
    private String caption; // Optional

    private List<String> mediaUrls;

    private Visibility visibility;

    private Boolean allowComment;

    private Boolean isPin;

    private String locationId;

    private Boolean isDeleted;

}

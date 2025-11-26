package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareUpdateDTO {

    private String locationId;

    private String caption;

    private Boolean isDeleted;
}

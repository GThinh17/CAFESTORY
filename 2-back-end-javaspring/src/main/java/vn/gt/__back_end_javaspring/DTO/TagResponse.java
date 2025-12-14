package vn.gt.__back_end_javaspring.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.Visibility;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    // id bai tag
    private String id;

    // id nguoi tag
    private String userId;

    // nguoi duoc tag va ten nguuoi do
    private String userIdTag;
    private String pageIdTag;
    private String username;

    // cac thanh phan cua blog
    private String blogIdTag;

}

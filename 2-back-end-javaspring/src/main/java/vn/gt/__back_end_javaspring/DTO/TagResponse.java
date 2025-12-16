package vn.gt.__back_end_javaspring.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.google.auto.value.AutoValue.Builder;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.enums.Visibility;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponse {
    private String id;

    private String userId;
    private String userName;
    private String blogTagId;

    private String userTagId;

    private String pageTagId;

}

package vn.gt.__back_end_javaspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CursorPage<T> {
    private List<T> data;
    private String nextCursor; //base64(createdAt |id)
}



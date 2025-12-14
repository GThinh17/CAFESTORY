package vn.gt.__back_end_javaspring.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleResponse {
    private String id;
    private String fullName;
    private List<String> userRole;
}

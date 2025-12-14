package vn.gt.__back_end_javaspring.entity.Embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import org.checkerframework.common.aliasing.qual.Unique;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    @Unique
    private String roleId;
}

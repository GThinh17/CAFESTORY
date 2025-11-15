package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ShareDetailId implements Serializable {
    @Column(name = "share_id")
    private String shareId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "blog_id")
    private String blogId;
}

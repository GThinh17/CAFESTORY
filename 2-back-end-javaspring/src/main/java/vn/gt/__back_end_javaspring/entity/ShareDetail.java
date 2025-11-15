package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "share_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareDetail {

    @EmbeddedId
    private ShareDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shareId")
    @JoinColumn(name = "share_id", nullable = false, foreignKey = @ForeignKey(name = "fk_share_detail_share"))
    private Share share;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_share_detail_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("blogId")
    @JoinColumn(name = "blog_id", nullable = false, foreignKey = @ForeignKey(name = "fk_share_detail_blog"))
    private Blog blog;

}



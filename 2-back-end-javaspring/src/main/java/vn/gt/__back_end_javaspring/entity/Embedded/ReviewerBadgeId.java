package vn.gt.__back_end_javaspring.entity.Embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class ReviewerBadgeId implements Serializable {
    @Column(name = "reviewer_id")
    private String reviewerId;

    @Column(name = "badge_id")
    private String badgeId;
}

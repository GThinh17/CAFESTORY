package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.PricingRule;
import vn.gt.__back_end_javaspring.entity.Reviewer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningEventResponse {
    private String earningEventId;

    private String reviewerId;

    private String reviewerName;

    private String reviewrAvatarUrl;

    private BigDecimal likeWeight; //Trong so cua like 1 like = 2 diem

    private BigDecimal commentWeight;

    private BigDecimal shareWeight;

    private BigDecimal unitPrice; //Gia tien tren moi diem

    private String commentId;

    private String likeId;

    private String shareId;

    private String blogId;

    private BigDecimal amount;

    private String sourceType;

    private LocalDateTime createdAt;
}

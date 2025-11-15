package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    public String getId() {
        return id;
    }

    public String getVisibility() {
        return visibility;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ShareDetail> getShareDetails() {
        return shareDetails;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setShareDetails(List<ShareDetail> shareDetails) {
        this.shareDetails = shareDetails;
    }

    @Column(name = "visibility", nullable = false, length = 20)
    private String visibility;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "share", fetch = FetchType.LAZY)
    private List<ShareDetail> shareDetails;
}

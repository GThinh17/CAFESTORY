// ShareResponseDTO: sửa blogId -> String & có visibility
package vn.gt.__back_end_javaspring.DTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ShareResponseDTO {
    private String shareId;

    public String getShareId() {
        return shareId;
    }

    public String getVisibility() {
        return visibility;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private String visibility;
    private LocalDateTime createdAt;
}

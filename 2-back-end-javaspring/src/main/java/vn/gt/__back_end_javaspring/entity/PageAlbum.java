package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.Visibility;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "page_album")
@AllArgsConstructor
@NoArgsConstructor
public class PageAlbum {
    @Id
    @Column(name = "page_album_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_id")
    private Page page;

    @Column(name = "title")
    private String title;

    @Column(name = "visibility")
    private Visibility visibility;


    @OneToMany(mappedBy = "pageAlbum", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<PageImage> images = new ArrayList<>();

    @Column(name = "total_photo")
    private Integer totalPhoto;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        this.totalPhoto = 0;
        this.isDeleted = false;
    }
}

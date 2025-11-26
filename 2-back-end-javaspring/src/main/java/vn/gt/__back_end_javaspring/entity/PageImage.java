package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "page_image")
@AllArgsConstructor
@NoArgsConstructor
public class PageImage {

    @Id
    @Column(name = "page_image_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_album_id")
    private PageAlbum pageAlbum;

    @Column(name = "image_url")
    private String imageUrl;
}

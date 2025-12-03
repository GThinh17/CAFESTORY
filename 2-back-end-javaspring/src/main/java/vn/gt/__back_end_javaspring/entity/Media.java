package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.gt.__back_end_javaspring.enums.MediaType;

@Data
@Entity
@Table(name = "media")
public class Media { //Check
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "media_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id") //Ten cot la blog_id
    private Blog blog;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "description")
    private String description;

}

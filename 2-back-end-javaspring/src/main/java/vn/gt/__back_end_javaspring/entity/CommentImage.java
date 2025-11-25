package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_image")
public class CommentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_image_id")
    private String id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;


}

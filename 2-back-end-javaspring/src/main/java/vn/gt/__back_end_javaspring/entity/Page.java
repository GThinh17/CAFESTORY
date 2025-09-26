
package vn.gt.__back_end_javaspring.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "pages")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false, length = 500)
    private String description;


    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "like", nullable = false)
    private Long like;

    @Column(name = "follower", nullable = false)
    private Long follower;
}
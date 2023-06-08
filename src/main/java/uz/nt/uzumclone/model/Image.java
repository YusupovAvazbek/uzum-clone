package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pathLarge;
    private String pathMedium;
    private String pathSmall;
    private String ext;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductDetails productDetails;
    @OneToMany(mappedBy = "image")
    private List<ImageQuality> imageQuality;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;

}

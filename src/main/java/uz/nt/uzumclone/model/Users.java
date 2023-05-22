package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(generator = "userIdSequence")
    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String phoneNumber;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Short isActive;
    private boolean enabled;
    @Column(columnDefinition = "text default('USER')")
    private String role;
    @OneToMany
    private List<Product> favorited;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}

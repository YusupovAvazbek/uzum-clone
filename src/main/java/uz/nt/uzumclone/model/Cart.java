package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(generator = "cartIdSeq")
    @SequenceGenerator(name = "cartIdSeq",sequenceName = "cart_id_seq",allocationSize = 1)
    private Integer id;
    @Column(name = "total_price")
    private Integer totalPrice;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdAt;



}

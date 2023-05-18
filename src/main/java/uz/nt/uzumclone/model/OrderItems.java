package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(generator = "orderItemsIdSeq")
    @SequenceGenerator(name = "orderItemsIdSeq", sequenceName = "order_items_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    private OrderDetails order;
    @OneToOne
    private Product product;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

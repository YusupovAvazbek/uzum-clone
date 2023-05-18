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
public class OrderDetails {
    @Id
    @GeneratedValue(generator = "orderDetailsIdSeq")
    @SequenceGenerator(name = "orderDetailsIdSeq", sequenceName = "order_details_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    private Users user;
    private Double total;
    @OneToOne
    private PaymentDetails paymentDetails;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

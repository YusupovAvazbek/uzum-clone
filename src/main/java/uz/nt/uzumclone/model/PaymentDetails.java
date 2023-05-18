package uz.nt.uzumclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class PaymentDetails {
    @Id
    @GeneratedValue(generator = "paymentIdSeq")
    @SequenceGenerator(name = "paymentIdSeq")
    private Integer id;
    private Integer orderId;
    private Integer amount;
    private String provider;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

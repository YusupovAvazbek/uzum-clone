package uz.nt.uzumclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(generator = "addressIdSeq")
    @SequenceGenerator(name = "addressIdSeq",sequenceName = "address_id_seq",allocationSize = 1)
    private Integer id;
    private String info;
    private String code;
    private String phoneNumber;
}

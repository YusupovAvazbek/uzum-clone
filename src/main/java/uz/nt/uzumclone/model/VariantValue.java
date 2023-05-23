package uz.nt.uzumclone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "variant_value")
public class VariantValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    private Integer valueId;
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
    private String value;
}

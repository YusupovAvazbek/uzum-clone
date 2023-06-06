package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "variant_value")
public class VariantValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    private Integer valueId;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private Variant variant;
    @OneToMany(mappedBy = "variantValue",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductDetails> productDetails = new ArrayList<>();
}

package uz.nt.uzumclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_variant")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    private Double price;
    private String sku;
    @ManyToMany
    @JoinTable(
            name = "product_details",
            joinColumns = @JoinColumn(name = "productVariant_id"),
            inverseJoinColumns = @JoinColumn(name = "variantValue_id")
    )
    private List<VariantValue> variantValues;

}

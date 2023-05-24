package uz.nt.uzumclone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.model.ProductVariant;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.EMPTY_STRING;
import static uz.nt.uzumclone.additional.AppStatusMessages.NEGATIVE_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    private Integer amount;
    @NotBlank(message = EMPTY_STRING)
    private String description;
    private BrandDto brand;
    private CategoryDto category;
    private List<ProductVariantDto> productVariants;
    private Boolean isAvailable;
}

package uz.nt.uzumclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.EMPTY_STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    private Integer amount;
    private Integer price;
    @NotBlank(message = EMPTY_STRING)
    private String description;
    private BrandDto brand;
    private CategoryDto category;
    private List<ProductVariantDto> productVariants;
    private Boolean isAvailable;
}

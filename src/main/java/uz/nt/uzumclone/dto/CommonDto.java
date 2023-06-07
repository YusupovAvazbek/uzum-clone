package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.domain.Page;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.model.Product;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CommonDto {
    @JsonIgnore
    private Set<BrandDto> brands;
    private Page<ProductDto> products;
    private List<CategoryDto> categories;
    public CommonDto(Set<BrandDto> brands, Page<ProductDto> products, List<CategoryDto> categories){
        this.brands = brands;
        this.products = products;
        this.categories = categories;
    }
}

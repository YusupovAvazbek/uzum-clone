package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Double price;
    private Integer amount;
    private String description;
    private CategoryDto category;
    private Boolean isAvailable;
}

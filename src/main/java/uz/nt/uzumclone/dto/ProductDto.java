package uz.nt.uzumclone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    private Double price;
    private Integer amount;
    @NotBlank(message = EMPTY_STRING)
    private String description;
    private CategoryDto category;
    private Boolean isAvailable;
}

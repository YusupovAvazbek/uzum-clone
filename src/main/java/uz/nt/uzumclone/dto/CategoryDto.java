package uz.nt.uzumclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import static uz.nt.uzumclone.additional.AppStatusMessages.EMPTY_STRING;
import static uz.nt.uzumclone.additional.AppStatusMessages.NEGATIVE_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    private List<CategoryDto> parentCategory;
}

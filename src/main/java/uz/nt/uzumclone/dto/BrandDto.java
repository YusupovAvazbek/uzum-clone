package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private Integer id;
    private String name;
    private LocalDateTime createdAt;
}

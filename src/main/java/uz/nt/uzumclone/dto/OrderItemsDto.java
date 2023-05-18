package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto {
    private Integer id;
    private OrderDetailsDto order;
    private ProductDto product;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

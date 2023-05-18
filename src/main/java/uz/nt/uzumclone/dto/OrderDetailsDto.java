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
public class OrderDetailsDto {
    private Integer id;
    private UsersDto user;
    private Double total;
    private PaymentDetailsDto payment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

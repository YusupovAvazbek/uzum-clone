package uz.nt.uzumclone.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto{
    private Integer id;
    @Pattern(regexp = "\\+998 \\d{2} \\d{3} \\d{2} \\d{2}")
    private String phoneNumber;
    @NotBlank
    @Size(min = 4)
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @Size(min = 3)
    private String password;

}

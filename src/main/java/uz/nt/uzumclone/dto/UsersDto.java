package uz.nt.uzumclone.dto;

import jakarta.persistence.Column;
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
    private String phoneNumber;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}

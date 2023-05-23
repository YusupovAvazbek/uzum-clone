package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private String description;
    private UsersDto users;
    private List<CommentDto> parentComment;

}

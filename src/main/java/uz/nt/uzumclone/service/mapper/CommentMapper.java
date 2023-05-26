package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.CommentDto;
import uz.nt.uzumclone.model.Comments;

@Mapper(componentModel = "spring")
public interface CommentMapper extends CommonMapper<CommentDto, Comments>{

}

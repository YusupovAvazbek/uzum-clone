package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.CommentDto;
import uz.nt.uzumclone.dto.ResponseDto;

import java.util.List;

public interface CommentService {
    ResponseDto<List<CommentDto>> viewAll(Integer id);
    ResponseDto<CommentDto> addComment(Integer id, String comment);
    ResponseDto<CommentDto> removeComment(Integer id);
}

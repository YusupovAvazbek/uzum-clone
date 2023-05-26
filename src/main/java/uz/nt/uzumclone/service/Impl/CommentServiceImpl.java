package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.CommentDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Comments;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.repository.CommentRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.CommentService;
import uz.nt.uzumclone.service.mapper.CommentMapper;
import uz.nt.uzumclone.service.mapper.UsersMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ProductRepository productRepository;
    private final UsersMapper usersMapper;
    @Override
    public ResponseDto<List<CommentDto>> viewAll(Integer id) {
        List<CommentDto> commentList = commentRepository.findAllByProduct_Id(id).stream().map(commentMapper:: toDto).toList();

        return ResponseDto.<List<CommentDto>>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .data(commentList)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<CommentDto> addComment(Integer id, String comment) {
        //UsersDto user = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .message(AppStatusMessages.NOT_FOUND)
                    .build();
        }
        Comments commentSave = Comments.builder()
                .users(usersMapper.toEntityPassword(new UsersDto()))
                .description(comment)
                .product(product.get())
                .build();
        try {
            CommentDto commentDto = commentMapper.toDto(commentRepository.save(commentSave));
            return ResponseDto.<CommentDto>builder()
                    .data(commentDto)
                    .message(AppStatusMessages.OK)
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR)
                    .data(commentMapper.toDto(commentSave))
                    .build();
        }
    }

    @Override
    public ResponseDto<CommentDto> removeComment(Integer id) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        UsersDto usersDto = new UsersDto();
        Optional<Comments> commentById = commentRepository.findById(id);
        if(commentById.isEmpty()){
            return ResponseDto.<CommentDto>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .success(false)
                    .code(AppStatusCodes.OK_CODE)
                    .build();
        }
        if(commentById.get().getUsers().getId() != usersDto.getId()){
            return ResponseDto.<CommentDto>builder()
                    .message("method not allow")
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        }

        Comments comment = commentById.get();
        commentRepository.deleteById(id);
        return ResponseDto.<CommentDto>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .success(true)
                .data(commentMapper.toDto(comment))
                .build();
    }
}

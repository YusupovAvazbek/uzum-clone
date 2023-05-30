package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CommentDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/product/comment")
@RequiredArgsConstructor
public class CommentResources {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer id){
        return commentService.viewAll(id);
    }
    @PostMapping("/{id}")
    public ResponseDto<CommentDto> writeComment(@RequestParam String comment,
                                                @PathVariable Integer id){
        return commentService.addComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer id){
        return commentService.removeComment(id);
    }


}

package uz.nt.uzumclone.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.uzumclone.additional.*;
import uz.nt.uzumclone.dto.ErrorDto;
import uz.nt.uzumclone.dto.ResponseDto;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandlerResources {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(AppStatusCodes.OK_CODE)
                        .message(AppStatusMessages.VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors().stream()
                                .map(f-> new ErrorDto(f.getField(),f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }
}

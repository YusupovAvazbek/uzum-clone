package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.ColorDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Color;
import uz.nt.uzumclone.repository.ColorRepository;
import uz.nt.uzumclone.service.ColorService;
import uz.nt.uzumclone.service.mapper.ColorMapper;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusCodes.DATABASE_ERROR_CODE;
import static uz.nt.uzumclone.additional.AppStatusCodes.NOT_FOUND_ERROR_CODE;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;


    @Override
    public ResponseDto<ColorDto> add(ColorDto colorDto) {
        try{
            Color color = colorMapper.toEntity(colorDto);
            colorRepository.save(color);

            return ResponseDto.<ColorDto>builder()
                    .success(true)
                    .message(OK)
                    .data(colorMapper.toDto(color))
                    .build();
        } catch (Exception e){
            return ResponseDto.<ColorDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<ColorDto> getById(Integer id) {
        return colorRepository.findById(id).map(
                p -> ResponseDto.<ColorDto>builder()
                        .success(true)
                        .message(OK)
                        .data(colorMapper.toDto(p))
                        .build()
        ).orElse(ResponseDto.<ColorDto>builder()
                .code(NOT_FOUND_ERROR_CODE)
                .message(NOT_FOUND)
                .build()
        );
    }

    @Override
    public ResponseDto<List<ColorDto>> getAll() {
        return ResponseDto.<List<ColorDto>>builder()
                .message(OK)
                .success(true)
                .data(colorRepository.findAll().stream().map(colorMapper::toDto).toList())
                .build();

    }
}

package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.ColorDto;
import uz.nt.uzumclone.dto.ResponseDto;

import java.util.List;

public interface ColorService {
    ResponseDto<ColorDto> add(ColorDto colorDto);
    ResponseDto<ColorDto> getById(Integer id);
    ResponseDto<List<ColorDto>> getAll();
}

package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.ColorDto;
import uz.nt.uzumclone.model.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper extends CommonMapper<ColorDto, Color> {
}

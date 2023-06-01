package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.VariantDto;
import uz.nt.uzumclone.model.Variant;

@Mapper(componentModel = "spring")
public interface VariantMapper extends CommonMapper<VariantDto, Variant>{
}

package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.VariantValueDto;
import uz.nt.uzumclone.model.VariantValue;
@Mapper(componentModel = "spring")
public interface VariantValueMapper extends CommonMapper<VariantValueDto, VariantValue> {
}

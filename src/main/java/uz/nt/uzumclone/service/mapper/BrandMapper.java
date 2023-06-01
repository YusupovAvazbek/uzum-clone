package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.model.Brand;
@Mapper(componentModel = "spring")
public interface BrandMapper extends CommonMapper<BrandDto, Brand> {
}

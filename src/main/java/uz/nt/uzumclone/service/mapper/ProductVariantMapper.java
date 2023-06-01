package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.ProductVariantDto;
import uz.nt.uzumclone.model.ProductVariant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper extends CommonMapper<List<ProductVariantDto>, List<ProductVariant>> {
}

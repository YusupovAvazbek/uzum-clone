package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ProductVariantDto;
import uz.nt.uzumclone.model.Product;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CommonMapper<ProductDto, Product> {

    @Autowired
    protected CategoryMapper categoryMapper;
    @Autowired
    protected ProductVariantMapper productVariantMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    @Mapping(target = "productVariants", expression = "java(productVariantMapper.toDto(product.getProductVariants()))")
    public abstract ProductDto toDto(Product product);
    @Mapping(target = "category", expression = "java(categoryMapper.toEntity(product.getCategory()))")
    @Mapping(target = "productVariants", expression = "java(productVariantMapper.toEntity(product.getProductVariants()))")
    public abstract Product toEntity(ProductDto product);
}

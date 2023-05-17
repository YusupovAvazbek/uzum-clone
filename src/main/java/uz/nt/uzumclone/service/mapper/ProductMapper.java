package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.model.Product;

public abstract class ProductMapper implements CommonMapper<ProductDto, Product> {
    @Autowired
    protected CategoryMapper categoryMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    public abstract ProductDto toDto(Product product);
}

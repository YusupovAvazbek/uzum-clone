package uz.nt.uzumclone.projections;

import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.model.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Integer getId();
    String getName();
    String getDescription();
    Integer getPrice();
    Integer getCategoryId();
    Integer getBrandId();
    Integer getDiscount();
    boolean isLiked();
}

package uz.nt.uzumclone.repository;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductCustomRepository {
    Page<Product> universalSearch(String query, String sorting, String ordering, Integer size, Integer currentPage);
    Page<Product> sort(Integer id, String sorting, String ordering, Integer currentPage);
    Page<Product> getProductByBrand(Integer id, List<String> brands, Integer currentPage);
    Page<Product> getByCategory(Integer id, Integer currentPage);
}

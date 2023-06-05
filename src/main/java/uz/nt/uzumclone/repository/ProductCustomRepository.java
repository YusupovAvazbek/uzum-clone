package uz.nt.uzumclone.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.projections.ProductProjection;

import java.util.List;
import java.util.Map;

public interface ProductCustomRepository {
    Page<Product> universalSearch(String query, String sorting, String ordering, Integer size, Integer currentPage);
    Page<ProductProjection> getWithSort(Integer id, String sorting, String ordering, Integer currentPage);
    Page<Product> getProductByBrand(Integer id, List<String> brands, Integer currentPage);
    List<ProductProjection> getProducts(Integer userId, Integer currentPage, Integer size);
    List<ProductProjection> getProductViewed(Integer userId);
    boolean insertViewedProduct(Integer userId, Integer productId);

}

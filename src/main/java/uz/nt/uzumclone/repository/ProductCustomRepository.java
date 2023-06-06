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
    Page<Product> universalSearch(String query, List<String> filter,String sorting, String ordering, Integer size, Integer currentPage);
    Page<Product> getWithSort(Integer id, List<String> filter,String sorting, String ordering, Integer currentPage);
    boolean insertViewedProduct(Integer userId, Integer productId);

}

package uz.nt.uzumclone.repository;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;

import java.util.Map;

public interface ProductCustomRepository {
    Page<Product> universalSearch(String query, Integer currentPage);
    Page<Product> sort(Integer id, String sorting, String ordering, Integer currentPage);

    Page<Product> getByCategory(Integer id,Integer currentPage);
}

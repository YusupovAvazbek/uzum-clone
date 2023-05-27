package uz.nt.uzumclone.repository;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.model.Product;

import java.util.Map;

public interface ProductCustomRepository {
    Page<Product> universalSearch(String query, Integer currentPage);
    Page<Product> sort(String sorting);
    Page<Product> getByCategory(Integer id,Integer currentPage);
}

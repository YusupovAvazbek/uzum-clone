package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.projections.ProductProjection;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductCustomRepository {

    @Query(value = "SELECT p.id AS id," +
            "       p.name AS name," +
            "       p.description AS description," +
            "       p.price AS price," +
            "       p.category_id AS categoryId," +
            "       p.brand_id AS brandId," +
            "       p.discount AS discount," +
            "CASE WHEN lp.user_id IS NOT NULL THEN true ELSE false END AS liked " +
            "FROM product p " +
            "LEFT JOIN liked_products lp ON p.id = lp.product_id AND lp.user_id = :userId " +
            "limit :size offset :currentPage*:size", nativeQuery = true)
    List<ProductProjection> getLikedProductUsingNativeQueryWithProjection(Integer userId, Integer currentPage, Integer size);

    @Query(value = "SELECT p.id AS id," +
            "       p.name AS name," +
            "       p.description AS description," +
            "       p.price AS price," +
            "       p.category_id AS categoryId," +
            "       p.brand_id AS brandId," +
            "       p.discount AS discount," +
            "CASE WHEN lp.user_id IS NOT NULL THEN true ELSE false END AS liked " +
            "FROM product p " +
            "LEFT JOIN liked_products lp ON p.id = lp.product_id AND lp.user_id = :userId ", nativeQuery = true)
    List<ProductProjection> getLikedProductUsingNativeQueryWithProjectionWithoutPagination(Integer userId);

    @Query(value = "SELECT p.id as id, p.name as name, p.description as description, p.price as price, p.category.id as categoryId, p.brand.id as brandId, p.discount as discount, case when u is not null then true else false end as liked " +
//            "CASE WHEN lp.user_id IS NOT NULL THEN true ELSE false END AS liked " +
            "FROM Product p " +
            "JOIN p.favourited u where u.id = :userId")
    List<ProductProjection> getLikedProductUsingJPQLWithProjectionWithoutPagination(Integer userId);
}

package uz.nt.uzumclone.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.projections.ProductProjection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FavoriteRepositoryImpl implements FavoriteCustomRepository{
    private final EntityManager entityManager;

    @Override
    @Transactional
    public boolean like(Integer userId, Integer productId) {
        Query query = entityManager.createNativeQuery("INSERT INTO liked_products(user_id, product_id) VALUES (:uId, :pId)")
                .setParameter("uId", userId)
                .setParameter("pId", productId);

        try {
            int i = query.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            log.error("error during insert liked_product table {}",e.getMessage());
            return false;
        }

    }

    @Override
    @Transactional
    public List<ProductProjection> getFavouriteByUser(Integer userId) {
        Query query = entityManager.createNativeQuery(
                "SELECT p.id AS id," +
                        "       p.name AS name," +
                        "       p.description AS description," +
                        "       p.price AS price," +
                        "       p.category_id AS categoryId," +
                        "       p.brand_id AS brandId," +
                        "       p.discount AS discount," +
                        "CASE WHEN lp.user_id IS NOT NULL THEN true ELSE false END AS liked " +
                        "FROM product p " +
                        "LEFT JOIN liked_products lp ON p.id = lp.product_id WHERE lp.user_id = :id",Object[].class
        ).setParameter("id",userId);

        List<Object[]> resultList = query.getResultList();


        List<ProductProjection> productProjections = new ArrayList<>();
        for (Object[] result : resultList) {
            Integer id = (Integer) result[0];
            String name = (String) result[1];
            String description = (String) result[2];
            Integer price = (Integer) result[3];
            Integer categoryId = (Integer) result[4];
            Integer brandId = (Integer) result[5];
            Integer discount = (Integer) result[6];
            boolean liked = (boolean) result[7];

            ProductProjection productProjection = new ProductProjection() {
                @Override
                public Integer getId() {
                    return id;
                }
                @Override
                public String getName() {
                    return name;
                }
                @Override
                public String getDescription() {
                    return description;
                }
                @Override
                public Integer getPrice() {
                    return price;
                }
                @Override
                public Integer getCategoryId() {
                    return categoryId;
                }
                @Override
                public Integer getBrandId() {
                    return brandId;
                }
                @Override
                public Integer getDiscount() {
                    return discount;
                }
                @Override
                public boolean isLiked() {
                    return liked;
                }
            };

            productProjections.add(productProjection);
        }

        return productProjections;
    }

    @Override
    public boolean unlike(Integer userId, Integer productId) {
        Query check = entityManager.createNativeQuery("SELECT * FROM loked_products WHERE user_id = :uId AND product_id = :pId");
        if(check.getResultList().isEmpty()){
            return false;
        }
        Query query = entityManager.createNativeQuery("DELETE FROM liked_products WHERE user_id = :uId AND product_id = :pId")
                .setParameter("uId", userId)
                .setParameter("pId", productId);

        try {
            int i = query.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            log.error("Error during delete from liked_products table: {}", e.getMessage());
            return false;
        }
    }
}

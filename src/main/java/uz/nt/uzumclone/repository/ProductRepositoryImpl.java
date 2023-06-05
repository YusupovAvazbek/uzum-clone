package uz.nt.uzumclone.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Category;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.rest.CategoryResources;
import uz.nt.uzumclone.rest.ProductResources;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Product> universalSearch(String query, String sorting, String ordering, Integer size, Integer currentPage) {
        size = Math.max(size, 0);
        int page = Math.max(currentPage,0);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);

        Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + query.toLowerCase() + "%");
        Predicate categoryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + query.toLowerCase() + "%");
        Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + query.toLowerCase() + "%");
        Predicate finalPredicate = criteriaBuilder.or(namePredicate, categoryPredicate, descriptionPredicate);

        criteriaQuery.where(finalPredicate);


        TypedQuery<Category> customQuery = entityManager.createQuery("select c from Category c where LOWER( c.name ) LIKE :query", Category.class);
                customQuery.setParameter("query","%"+query.toLowerCase()+"%");
        if (sorting != null && ordering != null) {
            Path<Object> sortPath;
            if (ordering.equalsIgnoreCase("ascending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.asc(sortPath));
            } else if (ordering.equalsIgnoreCase("descending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.desc(sortPath));
            }
        }

        TypedQuery<Product> search = entityManager.createQuery(criteriaQuery);

        long count = search.getResultList().size();


        if (count > 0 && count / size <= page){
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        search.setFirstResult(size * page);
        search.setMaxResults(size);

        if(search.getResultList().isEmpty()){
            if(!customQuery.getResultList().isEmpty()){
                return getWithSort(customQuery.getResultList().get(0).getId(),sorting,ordering,currentPage);
            }
        }


        return new PageImpl<>(search.getResultList(),PageRequest.of(page,size),count);

    }

    @Override
    public Page<Product> getWithSort(Integer id, String sorting, String ordering, Integer currentPage) {
        int size = 10;
        int page = Math.max(currentPage,0);

        List<Integer> categoryIds = getCategoriesWithChildren(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);

        criteriaQuery.where(root.get("category").get("id").in(categoryIds));

        if(sorting != null && ordering != null){
            Path<Object> sortPath = root.get(sorting);
            Order order = ordering.equalsIgnoreCase("ascending") ? criteriaBuilder.asc(sortPath) : criteriaBuilder.desc(sortPath);
            criteriaQuery.orderBy(order);
        }

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        long count = query.getResultList().size();
        if (count > 0 && count / size <= page) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return new PageImpl<>(query.getResultList(), PageRequest.of(page, size), count);

    }

    @Override
    public Page<Product> getProductByBrand(Integer id, List<String> brands, Integer currentPage){
        int size = 10;
        int page = Math.max(currentPage,0);
        List<Integer> categoryIds = getCategoriesWithChildren(id);

        Query query = entityManager
                .createQuery("SELECT p FROM Product p WHERE p.category.id IN :categoryIds and p.brand.name in :brandNames", Product.class)
                .setParameter("categoryIds", categoryIds)
                .setParameter("brandNames",brands);

        long count = query.getResultList().stream().count();

        if (count > 0 && count / size <= page) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        query.setFirstResult(size * page);
        query.setMaxResults(size);

        return new PageImpl<>(query.getResultList(), PageRequest.of(page,size), count);
    }

    @Override
    public List<ProductProjection> getProducts(Integer userId, Integer currentPage, Integer size) {
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
                        "LEFT JOIN liked_products lp ON p.id = lp.product_id AND lp.user_id = :id",Object[].class
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
    public List<ProductProjection> getProductViewed(Integer userId) {
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
                        "LEFT JOIN liked_products lp ON p.id = lp.product_id AND lp.user_id = :id "+
                        "LEFT JOIN viewed_products vp ON p.id = vp.product_id WHERE vp.user_id = :id",Object[].class
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
    @Transactional
    public boolean insertViewedProduct(Integer userId, Integer productId) {
        Query query = entityManager.createNativeQuery("INSERT INTO viewed_products(user_id, product_id) VALUES (:uId, :pId)")
                .setParameter("uId", userId)
                .setParameter("pId", productId);

        try {
            int i = query.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            log.error("error during insert viewed_product table {}",e.getMessage());
            return false;
        }
    }

    private List<Integer> getCategoriesWithChildren(Integer categoryId) {

        List<Integer> categoryIds = entityManager.createNativeQuery(
                        "WITH RECURSIVE child_categories AS (" +
                                "  SELECT id " +
                                "  FROM category " +
                                "  WHERE id = :categoryId " +
                                "  UNION ALL " +
                                "  SELECT c.id " +
                                "  FROM category c " +
                                "  JOIN child_categories cc ON cc.id = c.parent_category_id" +
                                ") " +
                                "SELECT id " +
                                "FROM child_categories"
                )
                .setParameter("categoryId", categoryId)
                .getResultList();

        return categoryIds;
    }



//    public List<Integer> getCategoriesWithChildren(Integer categoryId) {
//        List<Integer> categoryIds = new ArrayList<>();
//        getCategoriesRecursive(categoryId, categoryIds);
//        return categoryIds;
//    }
//
//    private void getCategoriesRecursive(Integer categoryId, List<Integer> categoryIds) {
//        categoryIds.add(categoryId);
//
//        List<Integer> childIds = entityManager.createQuery(
//                        "SELECT c.id FROM Category c WHERE c.parent_category_id = :categoryId",
//                        Integer.class
//                )
//                .setParameter("categoryId", categoryId)
//                .getResultList();
//
//        for (Integer childId : childIds) {
//            getCategoriesRecursive(childId, categoryIds);
//        }
//    }

}

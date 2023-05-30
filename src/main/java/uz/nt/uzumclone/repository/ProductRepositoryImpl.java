package uz.nt.uzumclone.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Category;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.rest.ProductResources;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Product> universalSearch(String query, Integer currentPage) {
        int size = 10;
        int page = Math.max(currentPage,0);
        TypedQuery<Product> search = entityManager.createQuery("SELECT p FROM Product p WHERE LOWER(p.name) LIKE :query OR LOWER(p.category.name) LIKE :query OR LOWER(p.description) LIKE :query", Product.class);
        search.setParameter("query", "%" + query.toLowerCase() + "%");

        TypedQuery<Category> customQuery = entityManager.createQuery("select c from Category c where LOWER( c.name ) LIKE :query", Category.class);
                customQuery.setParameter("query","%"+query.toLowerCase()+"%");

        if(search.getResultList().isEmpty()){
            if(!customQuery.getResultList().isEmpty()){
                return getByCategory(customQuery.getResultList().get(0).getId(),page);
            }
        }


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

        return new PageImpl<>(search.getResultList(),PageRequest.of(page,size),count);

    }

    @Override
    public Page<Product> sort(Integer id, String sorting, String ordering, Integer currentPage) {
        int size = 10;
        int page = Math.max(currentPage,0);

        List<Integer> categoryIds = getCategoriesWithChildren(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);

        criteriaQuery.where(root.get("category").get("id").in(categoryIds));

        if(sorting != null && ordering != null){
            Path<Object> sortPath = root.get(ordering);
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
    public Page<Product> getByCategory(Integer id, Integer currentPage) {
        int size = 10;
        int page = Math.max(currentPage,0);

        List<Integer> categoryIds = getCategoriesWithChildren(id);

        Query query = entityManager
                .createQuery("SELECT p FROM Product p WHERE p.category.id IN :categoryIds", Product.class)
                .setParameter("categoryIds", categoryIds);


        long count = (Long) entityManager.createQuery("SELECT count(p) FROM Product p WHERE p.category.id IN :categoryIds")
                        .setParameter("categoryIds", categoryIds)
                        .getSingleResult();

        if (count > 0 && count / size <= page) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        query.setFirstResult(size * page);
        query.setMaxResults(size);
//        EntityModel entityModel = EntityModel.of(query.getResultList());
//        entityModel.add(linkTo(ProductResources.class
//                .getDeclaredMethod()))

        return new PageImpl<>(query.getResultList(), PageRequest.of(page, size), count);

    }
    public Page<Product> getProductByBrand(Integer id, List<String> brands){
        List<Integer> categoryIds = getCategoriesWithChildren(id);

        Query query = entityManager
                .createQuery("SELECT p FROM Product p WHERE p.category.id IN :categoryIds and p.brand.name in :brandNames", Product.class)
                .setParameter("categoryIds", categoryIds)
                .setParameter("brandNames",brands);

        return null;

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

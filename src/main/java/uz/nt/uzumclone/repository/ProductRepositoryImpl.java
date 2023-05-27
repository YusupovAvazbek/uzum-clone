package uz.nt.uzumclone.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.Category;
import uz.nt.uzumclone.model.Product;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

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
                customQuery.setParameter("query",query.toLowerCase());

            if(!customQuery.getResultList().isEmpty()){
               return getByCategory(customQuery.getResultList().get(0).getId(),page);
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
    public Page<Product> sort(String sorting) {
        return null;
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


        return new PageImpl<>(query.getResultList(), PageRequest.of(page, size), count);

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

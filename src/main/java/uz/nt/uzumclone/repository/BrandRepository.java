package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.nt.uzumclone.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends CrudRepository<Brand, Integer> {

    @Query(value = "WITH ins AS (INSERT INTO brand(name) VALUES (?1) ON CONFLICT (name) DO NOTHING RETURNING *) SELECT * FROM ins UNION ALL SELECT * FROM brand WHERE name = ?1", nativeQuery = true)
    Optional<Brand> addIfNewBrand(String name);

    @Query(value = "SELECT * FROM Brand", nativeQuery = true)
    List<Brand> findAllBrands();
}

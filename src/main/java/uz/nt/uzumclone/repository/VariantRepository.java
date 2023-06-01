package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
}

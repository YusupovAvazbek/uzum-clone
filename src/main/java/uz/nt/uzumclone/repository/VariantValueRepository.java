package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.VariantValue;
@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, Integer> {
}

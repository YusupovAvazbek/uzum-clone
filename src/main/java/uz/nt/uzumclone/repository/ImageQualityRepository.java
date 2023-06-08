package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.ImageQuality;

@Repository
public interface ImageQualityRepository extends JpaRepository<ImageQuality, Integer> {
}

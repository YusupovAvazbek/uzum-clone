package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.uzumclone.model.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByIdAndIsActive(Integer id, short i);

    Optional<Users> findFirstByEmail(String email);

    Optional<Users> findFirstByPhoneNumberAndIsActive(String phoneNumber, short i);
    @Query(value = "INSERT INTO liked_products(user_id, product_id) VALUES (:userId, :productId)", nativeQuery = true)
    void insertLike(@Param("userId") Integer userId, @Param("productId") Integer productId);

}

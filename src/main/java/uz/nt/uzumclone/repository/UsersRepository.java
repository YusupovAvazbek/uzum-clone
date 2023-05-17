package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByIdAndIsActive(Integer id, short i);

    Optional<Users> findFirstByPhoneNumberAndIsActive(String phoneNumber, short i);
}

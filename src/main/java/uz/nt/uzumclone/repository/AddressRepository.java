package uz.nt.uzumclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}

package uz.nt.uzumclone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumclone.model.UserSession;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}

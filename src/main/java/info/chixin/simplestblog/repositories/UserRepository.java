package info.chixin.simplestblog.repositories;

import info.chixin.simplestblog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);
}

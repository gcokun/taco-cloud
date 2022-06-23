package gcokun.tacocloud.repository;

import gcokun.tacocloud.authentication.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}

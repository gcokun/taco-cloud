package gcokun.tacocloud.repository;

import gcokun.tacocloud.authentication.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
    Users findUserByUsername(String username);
}

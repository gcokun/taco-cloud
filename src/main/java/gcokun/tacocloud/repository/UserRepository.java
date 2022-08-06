package gcokun.tacocloud.repository;

import gcokun.tacocloud.authentication.UserInformation;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInformation, Long> {
    UserInformation findUserByUsername(String username);
}

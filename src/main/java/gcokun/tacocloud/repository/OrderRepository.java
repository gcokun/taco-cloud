package gcokun.tacocloud.repository;

import gcokun.tacocloud.authentication.UserInformation;
import gcokun.tacocloud.taco.TacoOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(UserInformation user, Pageable pageable);

}

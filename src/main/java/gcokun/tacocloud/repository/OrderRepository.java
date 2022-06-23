package gcokun.tacocloud.repository;

import gcokun.tacocloud.taco.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    TacoOrder save(TacoOrder tacoOrder);
}

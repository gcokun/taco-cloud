package gcokun.tacocloud.repository;

import gcokun.tacocloud.taco.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}

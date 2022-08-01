package gcokun.tacocloud.repository;

import gcokun.tacocloud.taco.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}

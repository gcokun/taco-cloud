package gcokun.tacocloud.repository;

import gcokun.tacocloud.taco.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}

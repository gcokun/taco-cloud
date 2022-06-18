package gcokun.tacocloud.repository;

import gcokun.tacocloud.taco.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("Select id, name, type from Ingredient",
                this::mapRowToIngredient).stream().toList();
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> ingredientList = jdbcTemplate.query("Select id, name, type from ıngredient where id = ?",
                this::mapRowToIngredient, id);
        return ingredientList.size() == 0 ?
                Optional.empty() :
                Optional.of(ingredientList.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("INSERT into Ingredient (id, name, type ) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int i) throws SQLException {
        return new Ingredient(resultSet.getString("id"),
                resultSet.getString("name"), Ingredient.Type.valueOf(resultSet.getString("type")));
    }
}

package gcokun.tacocloud.taco;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
public class IngredientRef {
    private final String ingredient;
}

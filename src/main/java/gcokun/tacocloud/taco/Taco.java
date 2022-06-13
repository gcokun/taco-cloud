package gcokun.tacocloud.taco;

import lombok.Data;

import java.util.List;

@Data
public class Taco {
    private String name;
    private List<Ingredient> ingredientList;
}

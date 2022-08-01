package gcokun.tacocloud;

import gcokun.tacocloud.authentication.RegistrationForm;
import gcokun.tacocloud.authentication.Users;
import gcokun.tacocloud.repository.IngredientRepository;
import gcokun.tacocloud.repository.OrderRepository;
import gcokun.tacocloud.repository.TacoRepository;
import gcokun.tacocloud.repository.UserRepository;
import gcokun.tacocloud.taco.Ingredient;
import gcokun.tacocloud.taco.Ingredient.Type;
import gcokun.tacocloud.taco.Taco;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository ingredientRepository,
                                        UserRepository userRepository,
                                        TacoRepository tacoRepository,
                                        OrderRepository orderRepository) {
        return args -> {
            Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
            Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
            Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
            Ingredient dicedTomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
            Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
            Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
            Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
            ingredientRepository.save(flourTortilla);
            ingredientRepository.save(cornTortilla);
            ingredientRepository.save(groundBeef);
            ingredientRepository.save(carnitas);
            ingredientRepository.save(dicedTomatoes);
            ingredientRepository.save(lettuce);
            ingredientRepository.save(cheddar);
            ingredientRepository.save(jack);
            ingredientRepository.save(salsa);
            ingredientRepository.save(sourCream);

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredientList(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
            tacoRepository.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Veggie");
            taco2.setIngredientList(Arrays.asList(cornTortilla, dicedTomatoes, lettuce, sourCream, salsa));
            tacoRepository.save(taco2);
        };
    }

}

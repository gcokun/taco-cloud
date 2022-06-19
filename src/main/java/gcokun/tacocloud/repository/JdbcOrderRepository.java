package gcokun.tacocloud.repository;

import gcokun.tacocloud.TacoOrder;
import gcokun.tacocloud.taco.Ingredient;
import gcokun.tacocloud.taco.Taco;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory creatorFactory =
                new PreparedStatementCreatorFactory(
                "insert into Taco_Order (delivery_name, delivery_street,delivery_city," +
                        "delivery_apt, cc_number, cc_expiration, cc_cvv, placed_at) " +
                        "values ( ?,?,?,?,?,?,?,?)",
                        Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
                        Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP);
        creatorFactory.setReturnGeneratedKeys(true);
        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator creator = creatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        tacoOrder.getDeliveryName(),
                        tacoOrder.getDeliveryStreet(),
                        tacoOrder.getDeliveryCity(),
                        tacoOrder.getDeliveryApt(),
                        tacoOrder.getCcNumber(),
                        tacoOrder.getCcExpiration(),
                        tacoOrder.getCcCVV(),
                        tacoOrder.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        tacoOrder.setId(orderId);
        List<Taco> tacoList = tacoOrder.getTacoList();
        int i = 0;
        for (Taco taco: tacoList) {
            saveTaco(orderId, i++, taco);
        }
        return tacoOrder;
    }

    private long saveTaco(long orderId, int orderKey, Taco taco) {
        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, taco_order, taco_order_key, created_at) values " +
                        "(?, ?, ?, ?)",
                Types.VARCHAR, Type.LONG, Type.LONG, Types.TIMESTAMP);
        taco.setCreatedAt(new Date());
        creatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator creator = creatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        orderId,
                        orderKey,
                        taco.getCreatedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredientList());
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredientList) {
        int key = 0;
        for (Ingredient ingredient : ingredientList) {
            jdbcOperations.update("insert into Ingredient_Ref (ingredient, taco, taco_key)" +
                    "values ( ?, ?, ? )", ingredient.getId(), tacoId, key++);
        }
    }
}

package gcokun.tacocloud.repository;

import gcokun.tacocloud.TacoOrder;
import gcokun.tacocloud.taco.Taco;
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
            save(orderId, i++, taco);
        }
        return tacoOrder;
    }

    private void save(long orderId, int i, Taco taco) {
        //TODO:
    }
}

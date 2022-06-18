package gcokun.tacocloud.repository;

import gcokun.tacocloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);
}

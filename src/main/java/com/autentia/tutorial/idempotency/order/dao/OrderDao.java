package com.autentia.tutorial.idempotency.order.dao;

import com.autentia.tutorial.idempotency.order.model.Order;
import java.util.List;

/**
 *
 * @author marlandy
 */
public interface OrderDao {

    Integer createOrderId();

    List<Order> getAll();

    Order getById(String id);

    void insert(Order order);

    void update(Order order);

}

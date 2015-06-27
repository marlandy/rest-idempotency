package com.autentia.tutorial.idempotency.order.service;

import com.autentia.tutorial.idempotency.order.model.Order;
import java.util.List;

/**
 *
 * @author marlandy
 */
public interface OrderService {

    public enum CreationStatus {

        CREATED, MODIFIED
    }

    List<Order> getAll();

    Order getById(String id);

    CreationStatus create(Order order);

}

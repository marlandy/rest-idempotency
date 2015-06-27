package com.autentia.tutorial.idempotency.order.service.impl;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.order.dao.OrderDao;
import com.autentia.tutorial.idempotency.order.model.Order;
import com.autentia.tutorial.idempotency.order.service.OrderKeyGenerator;
import com.autentia.tutorial.idempotency.order.service.OrderService;
import com.autentia.tutorial.idempotency.util.ValidationUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marlandy
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderKeyGenerator orderKeyGenerator;

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderKeyGenerator orderKeyGenerator, OrderDao orderDao) {
        this.orderKeyGenerator = orderKeyGenerator;
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order getById(String id) {
        ValidationUtil.validateRequired(id, "Order id");
        return orderDao.getById(id);
    }

    @Override
    public CreationStatus create(Order order) {
        verifyBasicOrderValidations(order);
        if (orderDao.getById(order.getId()) == null) {
            validateOrderId(order.getId());
            LOG.trace("Creating new order {}", order);
            orderDao.insert(order);
            return CreationStatus.CREATED;
        } else {
            LOG.trace("Updating order {}", order);
            orderDao.update(order);
            return CreationStatus.MODIFIED;
        }
    }

    private void verifyBasicOrderValidations(Order order) {
        ValidationUtil.validateRequired(order, "Order");
        ValidationUtil.validateRequired(order.getId(), "Order id");
        ValidationUtil.validateRequired(order.getPrice(), "Order price");
        ValidationUtil.validateRequired(order.getVouncher(), "Order vouncher");
        validateOrderStatus(order.getStatus());
    }

    private void validateOrderStatus(Integer status) {
        ValidationUtil.validateRequired(status, "Order status");
        ValidationUtil.validateOrderStatus(status);
    }

    private void validateOrderId(String id) {
        if (!orderKeyGenerator.isValidKey(id)) {
            LOG.error("Order id {} is not valid", id);
            throw new InvalidDataException("Order id " + id + " is invalid");
        }
    }

}

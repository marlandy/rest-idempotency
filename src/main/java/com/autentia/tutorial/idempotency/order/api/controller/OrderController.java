package com.autentia.tutorial.idempotency.order.api.controller;

import com.autentia.tutorial.idempotency.common.exception.NotFoundException;
import com.autentia.tutorial.idempotency.order.model.Order;
import com.autentia.tutorial.idempotency.order.model.Orders;
import com.autentia.tutorial.idempotency.order.service.OrderKeyGenerator;
import com.autentia.tutorial.idempotency.order.service.OrderService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marlandy
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final OrderKeyGenerator orderKeyGenerator;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderKeyGenerator orderKeyGenerator, OrderService orderService) {
        this.orderKeyGenerator = orderKeyGenerator;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Orders getAll() {
        LOG.trace("Request for get all orders ");
        final List<Order> orders = orderService.getAll();
        return new Orders(orders);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
    public Order getById(@PathVariable String orderId) {
        LOG.trace("Request for get order {}", orderId);
        final Order order = orderService.getById(orderId);
        if (order == null) {
            throw new NotFoundException();
        }
        return order;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createOrderKey(HttpServletRequest request) {
        LOG.trace("Request for new order key");
        final String key = orderKeyGenerator.generateKey();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + key);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<Order> createOrUpdate(@PathVariable String orderId, @RequestBody Order order) {
        LOG.trace("Request for create or update order {}", order);
        order.setId(orderId);
        OrderService.CreationStatus status = orderService.create(order);
        HttpStatus statusResponse;
        if (status == OrderService.CreationStatus.CREATED) {
            LOG.info("Order created {}", order);
            statusResponse = HttpStatus.CREATED;
        } else {
            LOG.info("Order updated {}", order);
            statusResponse = HttpStatus.OK;
        }
        return new ResponseEntity<>(order, statusResponse);
    }

}

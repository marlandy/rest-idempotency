package com.autentia.tutorial.idempotency.order.service;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.order.dao.OrderDao;
import com.autentia.tutorial.idempotency.order.model.Order;
import com.autentia.tutorial.idempotency.order.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author marlandy
 */
public class OrderServiceTest {

    private final OrderKeyGenerator mockedOrderKeyGenerator = mock(OrderKeyGenerator.class);

    private final OrderDao mockedOrderDao = mock(OrderDao.class);

    private OrderService orderService;

    @Before
    public void init() {
        orderService = new OrderServiceImpl(mockedOrderKeyGenerator, mockedOrderDao);
    }

    @Test
    public void shouldVerifyThatOrderIsNotNull() {
        try {
            orderService.create(null);
            fail("Should fail because order is null");
        } catch (InvalidDataException ide) {
            assertEquals("Order is required", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyThatIdIsNotNull() {
        try {
            orderService.create(createOrder(null, new Double(12), "", Order.CANCELED));
            fail("Should fail because order id is null");
        } catch (InvalidDataException ide) {
            assertEquals("Order id is required", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyThatPriceIsNotNull() {
        try {
            orderService.create(createOrder("1000_1234567890abcdef", null, "", Order.CANCELED));
            fail("Should fail because order price is null");
        } catch (InvalidDataException ide) {
            assertEquals("Order price is required", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyThatVouncherIsNotNull() {
        try {
            orderService.create(createOrder("1000_1234567890abcdef", new Double(2), null, Order.CANCELED));
            fail("Should fail because order price is null");
        } catch (InvalidDataException ide) {
            assertEquals("Order vouncher is required", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyThatStatusIsNotNull() {
        try {
            orderService.create(createOrder("1000_1234567890abcdef", new Double(2), "", null));
            fail("Should fail because order status is null");
        } catch (InvalidDataException ide) {
            assertEquals("Order status is required", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyThatStatusIsValid() {
        try {
            orderService.create(createOrder("1000_1234567890abcdef", new Double(2), "", 4));
            fail("Should fail because order status is not valid");
        } catch (InvalidDataException ide) {
            assertEquals("Order status must be: 1 (CREATED), 2 (FINISHED) or 3 (CANCELED)", ide.getMessage());
        }
    }

    @Test
    public void shouldVerifyIfOrderIdIsValidBeforeCreateAnOrder() {
        final Order orderWithInvalidId = createOrder("Invalid_id", new Double(34.5), "", Order.CREATED);
        when(mockedOrderKeyGenerator.isValidKey(orderWithInvalidId.getId())).thenReturn(false);
        when(mockedOrderDao.getById(orderWithInvalidId.getId())).thenReturn(null);
        try {
            orderService.create(orderWithInvalidId);
            fail("Should fail because order id is not valid");
        } catch (InvalidDataException ide) {
            assertEquals("Order id " + orderWithInvalidId.getId() + " is invalid", ide.getMessage());
        }
    }

    @Test
    public void shouldNotVerifyIfOrderIdIsValidWhenOrderIsUpdated() {
        final Order orderWithInvalidId = createOrder("Invalid_id", new Double(34.5), "", Order.CREATED);
        when(mockedOrderKeyGenerator.isValidKey(orderWithInvalidId.getId())).thenReturn(false);
        when(mockedOrderDao.getById(orderWithInvalidId.getId())).thenReturn(new Order());
        try {
            orderService.create(orderWithInvalidId);
        } catch (InvalidDataException ide) {
            fail("Should fail because order id must not validate when action is update");
        }
    }

    @Test
    public void shouldInsertNewOrderIfItDoesNotExist() {
        final Order newOrder = createOrder("valid_id", new Double(34.5), "", Order.CREATED);
        when(mockedOrderKeyGenerator.isValidKey(newOrder.getId())).thenReturn(true);
        when(mockedOrderDao.getById(newOrder.getId())).thenReturn(null);
        OrderService.CreationStatus status = orderService.create(newOrder);
        assertEquals(OrderService.CreationStatus.CREATED, status);
        verify(mockedOrderDao, times(1)).insert(newOrder);
        verify(mockedOrderDao, times(0)).update(newOrder);
    }

    @Test
    public void shouldUpdateOrderIfItAlreadyExists() {
        final Order existingOrder = createOrder("valid_id", new Double(34.5), "", Order.CREATED);
        when(mockedOrderKeyGenerator.isValidKey(existingOrder.getId())).thenReturn(true);
        when(mockedOrderDao.getById(existingOrder.getId())).thenReturn(new Order());
        OrderService.CreationStatus status = orderService.create(existingOrder);
        assertEquals(OrderService.CreationStatus.MODIFIED, status);
        verify(mockedOrderDao, times(0)).insert(existingOrder);
        verify(mockedOrderDao, times(1)).update(existingOrder);
    }

    private Order createOrder(String id, Double price, String vouncher, Integer status) {
        final Order order = new Order();
        order.setId(id);
        order.setPrice(price);
        order.setVouncher(vouncher);
        order.setStatus(status);
        return order;
    }

}

package com.autentia.tutorial.idempotency.order.dao;

import com.autentia.tutorial.idempotency.order.model.Order;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see src/test/resources/data.sql
 *
 * @author marlandy
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void shouldReturnNextValueInSequence() {
        Integer expectedFirstResult = 17000000;
        assertEquals(expectedFirstResult, orderDao.createOrderId());
        assertEquals((Integer) (expectedFirstResult + 7), orderDao.createOrderId());
        assertEquals((Integer) (expectedFirstResult + 7 * 2), orderDao.createOrderId());
        assertEquals((Integer) (expectedFirstResult + 7 * 3), orderDao.createOrderId());
        assertEquals((Integer) (expectedFirstResult + 7 * 4), orderDao.createOrderId());
    }

    @Test
    public void shouldReturnOrderById() {
        final String existingOrderId = "17953683_bd84fe278c6152c821a80ee6038effe6";
        final Order order = orderDao.getById(existingOrderId);
        assertNotNull(order);
        assertEquals(38.58, order.getPrice(), 0);
        assertEquals("AZX33F", order.getVouncher());
    }

    @Test
    public void shouldReturnAllOrders() {
        final List<Order> orders = orderDao.getAll();
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals("17953683_bd84fe278c6152c821a80ee6038effe6", orders.get(0).getId());
        assertEquals("17953690_bd84fe278c6152c821a80ee6038effe6", orders.get(1).getId());
    }

    @Test
    public void shouldReturnNullIfOrderIdDoesntExist() {
        final String unExistingOrderId = "999999_bd84fe278c6152c821a80ee6038effe6";
        final Order order = orderDao.getById(unExistingOrderId);
        assertNull(order);
    }

    @Test
    public void shouldCreateANewOrder() {
        String id = "222222_bd84fe278c6152c821a80ee6038effe6";
        double price = 12.89;
        String vouncher = "AFRO22";
        int status = Order.FINISHED;
        createOrder(id, price, vouncher, status);
        final Order orderFromDB = orderDao.getById(id);
        assertNotNull(orderFromDB);
        assertEquals(id, orderFromDB.getId());
        assertEquals(price, orderFromDB.getPrice(), 0);
        assertEquals(vouncher, orderFromDB.getVouncher());
        assertEquals(Order.FINISHED, orderFromDB.getStatus());
    }

    @Test
    public void shouldUpdateAnOrder() {
        String id = "3333333_bd84fe278c6152c821a80ee6038effe6";
        createOrder(id, 16.09, "VOUNCHER", Order.CREATED);
        final Order orderToUpDate = new Order();
        orderToUpDate.setId(id);
        orderToUpDate.setPrice(99.78);
        orderToUpDate.setVouncher("ANOTHER_VOUNCHER");
        orderToUpDate.setStatus(Order.CANCELED);
        orderDao.update(orderToUpDate);
        final Order orderFromDB = orderDao.getById(id);
        assertNotNull(orderFromDB);
        assertEquals(orderFromDB.getId(), orderToUpDate.getId());
        assertEquals(orderFromDB.getPrice(), orderToUpDate.getPrice(), 0);
        assertEquals(orderFromDB.getVouncher(), orderToUpDate.getVouncher());
        assertEquals(Order.CANCELED, orderToUpDate.getStatus());
    }

    private void createOrder(String id, double price, String vouncher, int status) {
        final Order order = new Order();
        order.setId(id);
        order.setPrice(price);
        order.setVouncher(vouncher);
        order.setStatus(status);
        orderDao.insert(order);
    }
}

package com.autentia.tutorial.idempotency.order.dao.jdbc;

import com.autentia.tutorial.idempotency.order.model.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author marlandy
 */
public class OrderMapper implements RowMapper<Order> {
    
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Order order = new Order();
        order.setId(rs.getString("id"));
        order.setPrice(rs.getDouble("price"));
        order.setVouncher(rs.getString("vouncher"));
        order.setStatus(rs.getInt("status"));
        return order;
    }
    
}

package com.autentia.tutorial.idempotency.order.dao.jdbc;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.order.dao.OrderDao;
import com.autentia.tutorial.idempotency.order.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author marlandy
 */
@Repository
public class OrderJdbcDao implements OrderDao {

    private static final Logger LOG = LoggerFactory.getLogger(OrderJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderJdbcDao(DataSource dataSource) {
        LOG.info("Inicializando OrderJdbcDao");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Order> getAll() {
        LOG.trace("Getting all orders");
        return jdbcTemplate.query("select * from orders order by id ASC",
                new OrderMapper());
    }

    @Override
    public Integer createOrderId() {
        LOG.trace("Getting user with next sequence value");
        return jdbcTemplate.queryForObject("select orders_seq.nextval",
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                });
    }

    @Override
    public Order getById(String id) {
        LOG.trace("Getting order with id {}", id);
        try {
            return jdbcTemplate.queryForObject("select * from orders where id = ?",
                    new Object[]{id}, new OrderMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("Order with id {} not found", id);
            return null;
        }
    }

    @Override
    public void insert(final Order order) {
        LOG.trace("Creating order {}", order);
        final String sql = "insert into orders (id, price, vouncher, status) values (?, ?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, order.getId());
                ps.setDouble(2, order.getPrice());
                ps.setString(3, order.getVouncher());
                ps.setInt(4, order.getStatus());
                return ps;
            }
        });
    }

    @Override
    public void update(final Order order) {
        final Order existingOrder = getById(order.getId());
        if (existingOrder == null) {
            LOG.warn("Orde to update doesn't exist. Order id: {}", order.getId());
            throw new InvalidDataException("Order with id " + order.getId() + " doesn't exist");
        }

        final String sql = "update orders set price = ?, vouncher = ?, status = ? where id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql);
                ps.setDouble(1, order.getPrice());
                ps.setString(2, order.getVouncher());
                ps.setInt(3, order.getStatus());
                ps.setString(4, order.getId());
                return ps;
            }
        });
    }

}

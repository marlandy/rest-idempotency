package com.autentia.tutorial.idempotency.user.dao.jdbc;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.user.dao.UserDao;
import com.autentia.tutorial.idempotency.user.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author marlandy
 */
@Repository
public class UserJdbcDao implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserJdbcDao(DataSource dataSource) {
        LOG.info("Initializing UserJdbcDao");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAll() {
        LOG.trace("Getting all users");
        return this.jdbcTemplate.query("select * from users order by id ASC",
                new UserMapper());
    }

    @Override
    public User getById(int id) {
        LOG.trace("Getting user with id {}", id);
        try {
            return jdbcTemplate.queryForObject("select * from users where id = ?",
                    new Object[]{id}, new UserMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("User with id {} not found", id);
            return null;
        }
    }

    @Override
    public int create(final User user) {
        LOG.trace("Creating user {}", user);

        final String sql = "insert into users (name, age) values (?, ?)";
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setInt(2, user.getAge());
                return ps;
            }
        }, holder);

        int teamId = holder.getKey().intValue();
        LOG.trace("User successfully created. Id: {}", teamId);
        return teamId;
    }

    @Override
    public void update(final User user) {
        final User existingUser = getById(user.getId());
        if (existingUser == null) {
            LOG.warn("User to update doen't exist. User id: {}", user.getId());
            throw new InvalidDataException("User with id " + user.getId() + " doesn't exist");
        }

        final String sql = "update users set name = ?, age = ? where id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getName());
                ps.setInt(2, user.getAge());
                ps.setInt(3, user.getId());
                return ps;
            }
        });

    }

}

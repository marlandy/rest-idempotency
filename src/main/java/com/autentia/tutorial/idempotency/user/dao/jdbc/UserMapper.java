package com.autentia.tutorial.idempotency.user.dao.jdbc;

import com.autentia.tutorial.idempotency.user.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author marlandy
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        return user;
    }
}

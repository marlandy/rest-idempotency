package com.autentia.tutorial.idempotency.user.dao;

import com.autentia.tutorial.idempotency.user.model.User;
import java.util.List;

/**
 *
 * @author marlandy
 */
public interface UserDao {

    List<User> getAll();

    User getById(int id);

    int create(User user);

    void update(User user);

}

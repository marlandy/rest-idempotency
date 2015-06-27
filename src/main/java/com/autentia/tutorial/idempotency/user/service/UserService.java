package com.autentia.tutorial.idempotency.user.service;

import com.autentia.tutorial.idempotency.user.model.User;
import java.util.List;

/**
 *
 * @author marlandy
 */
public interface UserService {

    public enum CreationStatus {

        CREATED, MODIFIED
    }

    List<User> getAll();

    User getById(Integer id);

    Integer insert(User user);

    void update(User user);

}

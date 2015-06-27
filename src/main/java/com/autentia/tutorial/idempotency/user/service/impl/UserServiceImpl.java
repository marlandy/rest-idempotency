package com.autentia.tutorial.idempotency.user.service.impl;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.user.dao.UserDao;
import com.autentia.tutorial.idempotency.user.model.User;
import com.autentia.tutorial.idempotency.user.service.UserService;
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
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        LOG.trace("Getting all users");
        return userDao.getAll();
    }

    @Override
    public User getById(Integer id) {
        LOG.trace("Getting user with id {}", id);
        ValidationUtil.validateRequired(id, "User id");
        return userDao.getById(id);
    }

    @Override
    public Integer insert(User user) {
        LOG.trace("Creating new user {}", user);
        validateUserContent(user);
        return userDao.create(user);
    }

    @Override
    public void update(User user) {
        LOG.trace("Updating user {}", user);
        validateUserContent(user);
        ValidationUtil.validateRequired(user.getId(), "User id");
        final User existingUser = userDao.getById(user.getId());
        if (existingUser == null) {
            LOG.error("User with id {} doesnt exist", user.getId());
            throw new InvalidDataException("User with id " + user.getId() + " doesnt exist");
        }
        userDao.update(user);
    }

    private void validateUserContent(User user) {
        ValidationUtil.validateRequired(user, "User");
        ValidationUtil.validateRequired(user.getName(), "User name");
        ValidationUtil.validateRequired(user.getAge(), "User age");
    }

}

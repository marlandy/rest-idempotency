package com.autentia.tutorial.idempotency.user.service;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.user.dao.UserDao;
import com.autentia.tutorial.idempotency.user.model.User;
import com.autentia.tutorial.idempotency.user.service.impl.UserServiceImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author marlandy
 */
public class UserServiceTest {

    private final UserDao mockedUserDao = mock(UserDao.class);

    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(mockedUserDao);
    }

    @Test
    public void shouldVerifyIfUserExistsBeforeUpdate() {
        final Integer unexistingUserId = 7777;
        final User user = createUser(unexistingUserId, 21, "Pepe");
        when(mockedUserDao.getById(user.getId())).thenReturn(null);
        try {
            userService.update(user);
            verifyZeroInteractions(mockedUserDao);
            fail("Should fail because user doesnt exist");
        } catch (InvalidDataException ide) {
            assertEquals("User with id " + user.getId() + " doesnt exist", ide.getMessage());
        }
    }

    @Test
    public void shouldFailIfUserAgeIsNull() {
        final User user = createUser(null, null, "Pepe");
        try {
            userService.insert(user);
            fail("Should fail because user age is null");
        } catch (InvalidDataException ide) {
            assertEquals("User age is required", ide.getMessage());
        }
    }

    @Test
    public void shouldFailIfUserNameIsNull() {
        final User user = createUser(null, 15, null);
        try {
            userService.insert(user);
            fail("Should fail because user name is null");
        } catch (InvalidDataException ide) {
            assertEquals("User name is required", ide.getMessage());
        }
    }

    @Test
    public void shouldNotVerifyIfUserIdIsNullBeforeInsert() {
        final User user = createUser(null, 21, "Pepe");
        when(mockedUserDao.create(user)).thenReturn(7);
        assertEquals(Integer.valueOf(7), userService.insert(user));
    }

    private User createUser(Integer id, Integer age, String name) {
        final User user = new User();
        user.setAge(age);
        user.setId(id);
        user.setName(name);
        return user;
    }

}

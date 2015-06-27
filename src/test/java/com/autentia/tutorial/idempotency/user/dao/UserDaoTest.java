package com.autentia.tutorial.idempotency.user.dao;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.user.model.User;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void shouldReturnExistingUser() {
        final User existingUser = userDao.getById(5000);
        assertNotNull(existingUser);
        assertEquals(Integer.valueOf(5000), existingUser.getId());
        assertEquals("Test user", existingUser.getName());
        assertEquals(Integer.valueOf(33), existingUser.getAge());
    }

    @Test
    public void shouldReturnAllUsers() {
        final List<User> users = userDao.getAll();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Test user", users.get(0).getName());
        assertEquals(Integer.valueOf(13), users.get(1).getAge());
    }

    @Test
    public void shouldReturnNullIfUserDoesntExist() {
        final User unexistingUser = userDao.getById(5555);
        assertNull(unexistingUser);
    }

    @Test
    public void shouldCreateNewUser() {
        Integer id = createUser("Manuel", 7);
        final User newUserFromDB = userDao.getById(id);
        assertNotNull(newUserFromDB);
        assertEquals(id, newUserFromDB.getId());
        assertEquals("Manuel", newUserFromDB.getName());
        assertEquals(Integer.valueOf(7), newUserFromDB.getAge());
    }

    @Test
    public void shouldUpdateExistingUser() {
        Integer id = createUser("Lucas", 46);
        final User user = new User();
        user.setId(id);
        user.setName("Antonio");
        user.setAge(34);
        userDao.update(user);
        final User userFromDB = userDao.getById(id);
        assertNotNull(userFromDB);
        assertEquals("Antonio", userFromDB.getName());
        assertEquals(Integer.valueOf(34), userFromDB.getAge());
    }

    @Test
    public void shouldThrowErrorIfUserToUpdateDoesntExist() {
        int unexistingUserId = 9999;
        final User unexistingUser = new User();
        unexistingUser.setId(unexistingUserId);
        unexistingUser.setName("Pepe");
        unexistingUser.setAge(57);
        try {
            userDao.update(unexistingUser);
            fail("Should fail because user to update doesn't exist");
        } catch (InvalidDataException ide) {
            assertEquals("User with id " + unexistingUserId + " doesn't exist", ide.getMessage());
        }
    }

    private Integer createUser(String name, int age) {
        final User newUser = new User();
        newUser.setName(name);
        newUser.setAge(age);
        return userDao.create(newUser);
    }

}

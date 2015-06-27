package com.autentia.tutorial.idempotency.user.api.controller;

import com.autentia.tutorial.idempotency.common.exception.NotFoundException;
import com.autentia.tutorial.idempotency.user.model.User;
import com.autentia.tutorial.idempotency.user.model.Users;
import com.autentia.tutorial.idempotency.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marlandy
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Users getAll() {
        LOG.trace("Request for return all users");
        return new Users(userService.getAll());
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public User getById(@PathVariable Integer userId) {
        LOG.trace("Request for get user {}", userId);
        final User user = userService.getById(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(HttpServletRequest request,
            @RequestBody User user) {
        LOG.trace("Request for create user {}", user);
        int generatedId = userService.insert(user);
        user.setId(generatedId);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + user.getId());
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> createOrUpdate(@PathVariable Integer userId, @RequestBody User user) {
        LOG.trace("Request for create or update user {}", userId);
        user.setId(userId);
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

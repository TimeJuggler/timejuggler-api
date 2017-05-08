package com.codekasteel.controllers;


import com.codekasteel.models.User;
import com.codekasteel.services.UserService;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a {@link User} and returns the newly created object.
     *
     * @param user The new User resource to be created.
     * @return Newly created {@link User} object.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> registerNewUser(@RequestBody User user) throws InvalidDataException {

        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * OfferedService for retrieving all the users.
     *
     * @return list of all users.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users,
                users.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {

        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}

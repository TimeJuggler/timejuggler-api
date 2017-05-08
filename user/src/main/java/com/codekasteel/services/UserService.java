package com.codekasteel.services;


import com.codekasteel.models.User;
import com.codekasteel.repositories.UserRepository;
import com.sun.media.sound.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user to be created.
     * @return the newly created user with ID set.
     */
    public User createUser(User user) throws InvalidDataException {
        return userRepository.save(user);
    }

    /**
     * Retrieves all the users from the database.
     *
     * @return list of all the users.
     */
    public List<User> getAllUsers() {
        return iterableToList(userRepository.findAll());
    }

    private List<User> iterableToList(Iterable<User> iterable) {
        List<User> users = new ArrayList<>();
        iterable.forEach(users::add);

        return users;
    }

    /**
     * Retrieves a single user from the database.
     *
     * @param userID the ID of the user to retrieve.
     * @return the {@link User}
     */
    public User getUser(Long userID) {
        return userRepository.findOne(userID);
    }
}

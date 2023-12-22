package ru.demo.repositories;

import ru.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositories {

    List<User> getAllUsers();

    User findUserById(Long id);

    boolean isUsernameUniqueForUpdate(String username, Long currentUserId);

    User findUserByUsername(String username);

    Optional<User> getUserByUsername(String username);

    void save(User user);

    void delete(Long id);

    void update(User user);
}

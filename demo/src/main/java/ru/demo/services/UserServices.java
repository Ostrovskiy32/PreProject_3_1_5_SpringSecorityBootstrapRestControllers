package ru.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.demo.model.User;

import java.util.List;

public interface UserServices extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    void save(User user);

    void delete(Long id);

    void update(User user);

    boolean isUsernameUnique(String username);

    boolean isUsernameUniqueForUpdate(String username, Long currentUserId);
}

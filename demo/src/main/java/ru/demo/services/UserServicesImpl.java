package ru.demo.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.model.Role;
import ru.demo.model.User;
import ru.demo.repositories.UserRepositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServicesImpl implements UserServices{

    private final UserRepositories userRepositories;
    PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    public UserServicesImpl(UserRepositories userRepositories, PasswordEncoder passwordEncoder) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositories.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userRepositories.findUserById(id);
    }

    @Override
    public boolean isUsernameUniqueForUpdate(String username, Long currentUserId) {
        return userRepositories.isUsernameUniqueForUpdate(username, currentUserId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepositories.findUserByUsername(username);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        Optional<User> usrOpt = userRepositories.getUserByUsername(username);
        return usrOpt.isPresent();
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepositories.delete(id);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(@NotNull Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}

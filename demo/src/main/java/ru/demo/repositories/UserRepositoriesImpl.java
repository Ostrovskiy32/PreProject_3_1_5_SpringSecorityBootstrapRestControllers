package ru.demo.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoriesImpl implements UserRepositories {

    @PersistenceContext
    EntityManager entityManager;

    private static final String USERNAME_PARAM = "username";

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public boolean isUsernameUniqueForUpdate(String username, Long currentUserId) {

        List<Long> userIds = entityManager.createQuery(
                        "SELECT u.id FROM User u WHERE u.username = :username AND u.id <> :currentUserId", Long.class)
                .setParameter(USERNAME_PARAM, username)
                .setParameter("currentUserId", currentUserId)
                .getResultList();

        return userIds.isEmpty();
    }


    @Override
    public User findUserByUsername(String username) {
        return entityManager.createQuery("select user from User user where user.username = :username", User.class)
                .setParameter(USERNAME_PARAM, username)
                .setMaxResults(1).getSingleResult();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        try {
            User user = entityManager.createQuery("select user from User user where user.username = :username", User.class)
                    .setParameter(USERNAME_PARAM, username)
                    .setMaxResults(1).getSingleResult();
            return Optional.ofNullable(user);
        }catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override

    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findUserById(id));
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}

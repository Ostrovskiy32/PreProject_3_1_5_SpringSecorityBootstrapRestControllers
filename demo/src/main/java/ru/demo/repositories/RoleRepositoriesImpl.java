package ru.demo.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.demo.model.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepositoriesImpl implements RoleRepositories {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveRoles(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getById(Long id) {
        return entityManager.find(Role.class, id);
    }
}

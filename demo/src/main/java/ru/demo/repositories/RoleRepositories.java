package ru.demo.repositories;

import ru.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepositories {

    void saveRoles(Role role);

    Role getById(Long id);
}

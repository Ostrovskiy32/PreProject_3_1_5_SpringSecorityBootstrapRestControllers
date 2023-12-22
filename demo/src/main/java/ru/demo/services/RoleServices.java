package ru.demo.services;

import ru.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleServices {

    Role getById(Long id);

    void saveRole(Role role);
}

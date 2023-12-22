package ru.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.model.Role;
import ru.demo.repositories.RoleRepositories;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServicesImpl implements RoleServices {

    private final RoleRepositories roleRepositories;

    @Autowired
    public RoleServicesImpl(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    @Override
    public Role getById(Long id) {
        return  roleRepositories.getById(id);
    }

    @Override
    public void saveRole(Role role) {
        roleRepositories.saveRoles(role);
    }
}

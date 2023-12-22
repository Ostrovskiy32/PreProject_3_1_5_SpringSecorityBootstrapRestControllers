package ru.demo.util;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.model.Role;
import ru.demo.model.User;
import ru.demo.services.RoleServices;
import ru.demo.services.UserServices;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class InitFile {

    UserServices userService;
    RoleServices roleService;

    @Autowired
    public InitFile(UserServices userService, RoleServices roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Transactional
    @PostConstruct
    public void run() {

        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(roleService.getById(2l));
        adminRole.add(roleService.getById(1L));
        userRole.add(roleService.getById(2L));

        userService.save(new User("Igor", "Ostrovsky", (byte) 52, "Russian", "admin", "12345", adminRole));
        userService.save(new User("Peter", "Petrov", (byte) 25, "Russian", "user", "54321", userRole));
    }
}

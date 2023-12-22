package ru.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.demo.model.User;
import ru.demo.services.UserServices;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Valid
@RestController
@RequestMapping("/api")
public class AdminRestControllers {

    private final UserServices userServices;

    @Autowired
    public AdminRestControllers(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<User> getAuthUser(Principal principal) {
        return new ResponseEntity<>(userServices.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userServices.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userServices.getUserById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/save")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newUser, BindingResult bindingResult) {
        if (userServices.isUsernameUnique(newUser.getUsername())) {
            bindingResult.rejectValue("username", "error.newUser", "Username all ready exists");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        userServices.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User saved");
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userServices.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User updateUser, BindingResult bindingResult) {
        if (!userServices.isUsernameUniqueForUpdate(updateUser.getUsername(), id)) {
            bindingResult.rejectValue("username", "error.newUser", "Username all ready exists");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        updateUser.setUserId(id);
        userServices.update(updateUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}


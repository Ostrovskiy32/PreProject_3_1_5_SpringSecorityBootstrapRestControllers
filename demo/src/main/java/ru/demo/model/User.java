package ru.demo.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.demo.serialization.CustomUserSerializer;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@JsonSerialize (using = CustomUserSerializer.class)
public class User implements UserDetails {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    @NotBlank(message = "Field should not empty")
    @Pattern(regexp = "^[^\\d]*$", message = "The field must not contain numbers")
    @Size(min = 1, max =50, message = "Name must have bigger than 0, but no more than 50")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Field should not empty")
    @Pattern(regexp = "^[^\\d]*$", message = "The field must not contain numbers")
    @Size(min = 1, max =50, message = "Name must have bigger than 0, but no more than 50")
    private String surname;

    @Column(name = "age")
    @NotNull(message = "Field should not empty")
    @Min(value = 0, message = "No one don't live that like you")
    @Max(value = 127, message = "No one don't live that long like you)")
    private Byte age;

    @Column(name = "citizenship")
    @NotBlank(message = "Field should not empty")
    @Size(min = 1, max =50, message = "Name must have bigger than 0, but no more than 50")
    private String citizenship;


    @Column(name = "username", unique = true)
    @NotBlank(message = "Field should not empty")
    @Size(min = 1, max =50, message = "Name must have bigger than 0, but no more than 50")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Field should not be empty")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "userId"),
               inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String surname, Byte age, String citizenship, String username, String password, Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.citizenship = citizenship;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(age, user.age) && Objects.equals(citizenship, user.citizenship) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname, age, citizenship, username, password, roles);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long usrId) {
        this.userId = usrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Long> getRoleIds() {
        return roles.stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

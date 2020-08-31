package ru.job4j.forum.model;

import javax.persistence.*;
import java.util.*;

/**
 * The user class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    private static int counter = 0;

    public static int newId() {
        return ++counter;
    }

    public static User of(String name, String password, Set<Authority> authorities) {
        User user = new User();
//        user.setId(User.newId());
        user.setName(name);
        user.setPassword(password);
        user.setAuthorities(authorities);
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

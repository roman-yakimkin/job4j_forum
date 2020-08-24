package ru.job4j.forum.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The user class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
public class User {
    private int id;
    private String name;
    private String password;
    private List<Authority> authorities = new ArrayList<>();

    private static int counter = 0;

    public static int newId() {
        return ++counter;
    }

    public static User of(String name, String password, List<Authority> authorities) {
        User user = new User();
        user.setId(User.newId());
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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
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

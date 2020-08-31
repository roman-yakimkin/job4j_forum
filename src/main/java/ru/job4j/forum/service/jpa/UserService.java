package ru.job4j.forum.service.jpa;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The User service class via jpa
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 30.08.2020
 * @version 1.0
 */
@Service("JpaUserService")
public class UserService {
    private AuthorityService authorityService;
    private final UserRepository users;

    public User getCurrentUser() {
        return getByName("root");
    }

    public UserService(AuthorityService authorityService, UserRepository users) {
        this.authorityService = authorityService;
        this.users = users;
    }

    public User get(int id) {
        return users.findById(id).orElse(null);
    }

    public User getByName(String username) {
        return users.findByName(username).orElse(null);
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        users.findAll().forEach(result::add);
        return result;
    }

    public void delete(int id) {
        users.deleteById(id);
    }
}

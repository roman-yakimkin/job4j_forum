package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The User service class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
@Service
public class UserService {
    private AuthorityService authorityService;
    private Map<Integer, User> users = new HashMap<>();

    public User getCurrentUser() {
        return getByName("root");
    }

    public UserService(AuthorityService authorityService) {
        this.authorityService = authorityService;
        Authority admin = authorityService.getOne("ROLE_ADMIN");
        Authority poster = authorityService.getOne("ROLE_POSTER");
        Authority commenter = authorityService.getOne("ROLE_COMMENTER");

        User root = User.of("root", "password", List.of(admin, poster, commenter));
        User poster1 = User.of("ivanov", "123456", List.of(poster, commenter));
        User poster2 = User.of("petrov", "123456", List.of(poster, commenter));
        User commenterOnly = User.of("novice", "123", List.of(commenter));

        users.put(root.getId(), root);
        users.put(poster1.getId(), poster1);
        users.put(poster2.getId(), poster2);
        users.put(commenterOnly.getId(), commenterOnly);
    }

    private List<User> getList(Predicate<User> f) {
        return users.values().stream().filter(f).collect(Collectors.toList());
    }

    private User getElement(Predicate<User> f) {
        return users.values().stream().filter(f).findFirst().orElse(null);
    }


    public User get(int id) {
        return users.get(id);
    }

    public User getByName(String username) {
        return getElement(user -> user.getName().equals(username));
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public void delete(int id) {
        users.remove(id);
    }
}

package ru.job4j.forum.service.jpa;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;
import ru.job4j.forum.service.UserInfoProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The User service class via jpa
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 30.08.2020
 * @version 1.0
 */
@Service("JpaUserService")
public class UserService {
    private final AuthorityService authorityService;
    private final UserRepository users;
    private final UserInfoProvider userInfoProvider;

    public User getCurrentUser() throws IllegalArgumentException {
        return getByName(userInfoProvider.getUserName());
    }

    public UserService(AuthorityService authorityService, UserRepository users, UserInfoProvider userInfoProvider) {
        this.authorityService = authorityService;
        this.users = users;
        this.userInfoProvider = userInfoProvider;
    }

    public User get(int id) throws IllegalArgumentException {
        return users.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User getByName(String username) throws IllegalArgumentException {
        return users.findByName(username).orElseThrow(IllegalArgumentException::new);
    }

    public boolean userHasAuthority(User user, String authorityName) {
        return user != null && user.getAuthorities().stream().anyMatch(authority -> authority.getId().equals(authorityName));
    }

    public boolean userHasAuthority(User user, Set<String> authorityNames) {
        return user != null && user.getAuthorities().stream().anyMatch(authority -> authorityNames.contains(authority.getId()));
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        users.findAll().forEach(result::add);
        return result;
    }

    public void delete(int id) {
        users.deleteById(id);
    }

    public void save(User user) {
        users.save(user);
    }
}
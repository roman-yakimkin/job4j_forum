package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The authority service class
 * @author Roman Yakimin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
@Service
public class AuthorityService {
    Map<String, Authority> authorities = new HashMap<>();

    public AuthorityService() {
        Authority admin = Authority.of("ROLE_ADMIN", "Forum administrator. Can create and edit any posts");
        Authority poster = Authority.of("ROLE_POSTER", "Forum poster. Can create and edit his posts");
        Authority commenter = Authority.of("ROLE_COMMENTER", "Forum commenter. Can create and edit his comments");
        authorities.put(admin.getId(), admin);
        authorities.put(poster.getId(), poster);
        authorities.put(commenter.getId(), commenter);
    }

    public Authority getOne(String role) {
        return authorities.get(role);
    }

    public List<Authority> getAll() {
        return new ArrayList<>(authorities.values());
    }
}

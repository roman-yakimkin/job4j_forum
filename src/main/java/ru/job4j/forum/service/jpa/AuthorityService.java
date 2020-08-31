package ru.job4j.forum.service.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The authority service class via jpa
 * @author Roman Yakimin (r.yakimkin@yandex.ru)
 * @since 29.08.2020
 * @version 1.0
 */
@Service("JpaAuthorityService")
public class AuthorityService {
    private final AuthorityRepository authorities;

    public AuthorityService(AuthorityRepository authorities) {
        this.authorities = authorities;
    }

    public Authority getOne(String role) {
        return authorities.findById(role).orElse(null);
    }

    public List<Authority> getAll() {
        List<Authority> result = new ArrayList<>();
        authorities.findAll().forEach(result::add);
        return result;
    }
}

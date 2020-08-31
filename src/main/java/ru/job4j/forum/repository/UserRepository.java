package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.User;

import java.util.Optional;

/**
 * The JPA interface for user entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.08.2020
 * @version 1.0
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String name);
}

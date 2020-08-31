package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Authority;

/**
 * The JPA interface for authority entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.08.2020
 * @version 1.0
 */
public interface AuthorityRepository extends CrudRepository<Authority, String> {
}

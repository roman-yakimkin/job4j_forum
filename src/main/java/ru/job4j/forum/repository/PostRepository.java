package ru.job4j.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.List;

/**
 * The JPA interface for post entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.08.2020
 * @version 1.0
 */
public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByOrderByChangedDesc();
    Page<Post> findByAuthor(User author, Pageable pageable);
}

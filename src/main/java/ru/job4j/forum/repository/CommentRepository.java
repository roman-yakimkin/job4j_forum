package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Comment;

import java.util.List;

/**
 * The JPA interface for comment entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.08.2020
 * @version 1.0
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByPostId(int postId);
    List<Comment> findByPostIdAndParentIsNull(int postId);
    List<Comment> findByParentId(int parentId);
    List<Comment> findByParent(Comment parent);
}

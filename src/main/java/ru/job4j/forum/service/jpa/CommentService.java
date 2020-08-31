package ru.job4j.forum.service.jpa;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.job4j.forum.comparator.SortByTimeDescComparator;
import ru.job4j.forum.event.BeforePostDeleteEvent;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.CommentRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The comment service class for jpa
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 30.08.2020
 * @version 1.0
 */
@Service("JpaCommentsService")
public class CommentService {
    private final CommentRepository comments;

    public CommentService(CommentRepository comments) {
        this.comments = comments;
    }

    public List<Comment> getAll() {
        List<Comment> result = new ArrayList<>();
        comments.findAll().forEach(result::add);
        return result;
    }

    public Comment get(int id) {
        return comments.findById(id).orElse(null);
    }

    public List<Comment> getByPost(int postId) {
        return comments.findByPostId(postId);
    }

    public boolean isBelongToPost(int id, int postId) {
        Comment result = comments.findById(id).orElse(null);
        return (result != null && result.getPost().getId() == postId);
    }

    public List<Comment> getChildrenByParent(int parentId) {
        return comments.findByParentId(parentId);
    }

    public List<Comment> getChildrenByParent(Comment parent) {
        return comments.findByParent(parent);
    }

    public List<Comment> getAllChildrenByParent(Comment parent) {
        List<Comment> result = new ArrayList<>();
        List<Comment> childrenByParent = getChildrenByParent(parent);
        childrenByParent.sort(new SortByTimeDescComparator());
        for (Comment comment : childrenByParent) {
            result.add(comment);
            List<Comment> children = getAllChildrenByParent(comment);
            if (children.size() > 0) {
                result.addAll(children);
            }
        }
        return result;
    }

    public List<Comment> getTreeByPost(int postId) {
        List<Comment> result = new ArrayList<>();
        List<Comment> directChildren = comments.findByPostIdAndParentIsNull(postId);
        for (Comment child : directChildren) {
            result.add(child);
            List<Comment> children = getAllChildrenByParent(child);
            if (children.size() > 0) {
                result.addAll(children);
            }
        }
        return result;
    }

    public void delete(int id) {
        comments.deleteById(id);
        getChildrenByParent(id).forEach(comments::delete);
    }

    public void delete(Comment comment) {
        getChildrenByParent(comment).forEach(comments::delete);
        comments.delete(comment);
    }

    public void deleteByPost(int postId) {
        comments.deleteAll(comments.findByPostId(postId));
    }

    public void save(Comment comment) {
        comment.setDepth(comment.getParent() != null ? comment.getParent().getDepth() + 1 : 0);
        comment.setChanged(new GregorianCalendar());
        comments.save(comment);
    }

    public List<Comment> getLatestByUser(User user, int count) {
        return  getAll()
                .stream()
                .filter(comment -> comment.getAuthor().equals(user))
                .sorted(new SortByTimeDescComparator())
                .limit(count)
                .collect(Collectors.toList());
    }

    @EventListener
    public void onBeforePostDelete(BeforePostDeleteEvent event) {
        deleteByPost(event.getPost().getId());
    }

}

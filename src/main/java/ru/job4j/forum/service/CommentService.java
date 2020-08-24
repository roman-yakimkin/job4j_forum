package ru.job4j.forum.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.job4j.forum.event.BeforePostDeleteEvent;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The comment service class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 19.08.2020
 * @version 1.0
 */
@Service
public class CommentService {
    private Map<Integer, Comment> comments = new HashMap<>();

    public CommentService(PostService postService, UserService userService) {
        Post first = postService.get(1);
        Post second = postService.get(2);
        Post third = postService.get(3);
        User root = userService.getByName("root");
        User poster1 = userService.getByName("ivanov");
        User commenterOnly = userService.getByName("novice");

        Comment comment1 = Comment.of(first, null, root, "A comment from root user");
        comments.put(comment1.getId(), comment1);
        Comment comment11 = Comment.of(first, comment1, commenterOnly, "Could you explain your comment ?");
        comments.put(comment11.getId(), comment11);
        Comment comment111 = Comment.of(first, comment11, poster1, "Unlike you I understood what he meant");
        comments.put(comment111.getId(), comment111);
        Comment comment2 = Comment.of(second, null, poster1, "I think there will no future");
        comments.put(comment2.getId(), comment2);
        Comment comment21 = Comment.of(second, comment2, commenterOnly, "I believe in Drupal");
        comments.put(comment21.getId(), comment21);
        Comment comment3 = Comment.of(third, null, commenterOnly, "Laravel forever");
        comments.put(comment3.getId(), comment3);
        Comment comment31 = Comment.of(third, comment3, root, "Don't write anything without a substantiation! Or I will have to ban your account");
        comments.put(comment31.getId(), comment31);
    }

    public List<Comment> getAll() {
        return new ArrayList<>(comments.values());
    }

    private List<Comment> getList(Predicate<Comment> f) {
        return comments.values().stream().filter(f).collect(Collectors.toList());
    }

    private Comment getElement(Predicate<Comment> f) {
        return comments.values().stream().filter(f).findFirst().orElse(null);
    }

    public Comment get(int id) {
        return comments.get(id);
    }

    public List<Comment> getByPost(int postId) {
        return getList(comment -> comment.getPost().getId() == postId);
    }

    public boolean isBelongToPost(int id, int postId) {
        return (getElement(comment -> (comment.getId() == id && comment.getPost().getId() == postId)) != null);
    }

    public List<Comment> getChildrenByParent(int parentId) {
        return getList(comment -> comment.getParent() != null && comment.getParent().getId() == parentId);
    }

    public List<Comment> getChildrenByParent(Comment parent) {
        return getList(comment -> parent.equals(comment.getParent()));
    }

    public List<Comment> getAllChildrenByParent(Comment parent) {
        List<Comment> result = new ArrayList<>();
        for (Comment comment : getChildrenByParent(parent)) {
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
        List<Comment> directChildren = getList(comment -> comment.getPost().getId() == postId && comment.getParent() == null);
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
        comments.remove(id);
        getChildrenByParent(id).forEach(child -> comments.remove(child.getId()));
    }

    public void deleteByPost(int postId) {
        getList(comment -> comment.getPost().getId() == postId).forEach(child -> comments.remove(child.getId()));
    }

    public void save(Comment comment) {
        comment.setDepth(comment.getParent() != null ? comment.getParent().getDepth() + 1 : 0);
        comment.setChanged(new GregorianCalendar());

        comments.put(comment.getId(), comment);
    }

    @EventListener
    public void onBeforePostDelete(BeforePostDeleteEvent event) {
        deleteByPost(event.getPost().getId());
    }

}

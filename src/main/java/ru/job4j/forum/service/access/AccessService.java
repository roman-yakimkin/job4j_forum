package ru.job4j.forum.service.access;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.jpa.AuthorityService;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * The service for access to entities
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 04.09.2020
 * @version 1.0
 */
@Service
public class AccessService {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final AuthorityService authorityService;

    private Map<AccessEntityOperation, BiFunction<Object, User, Boolean>> permissions = new HashMap<>();

    public AccessService(UserService userService, PostService postService, CommentService commentService, AuthorityService authorityService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.authorityService = authorityService;

        initPermissions();
    }

    private Map<AccessOperation, Boolean> getAccessPermissions() {
        try {
            return getAccessPermissions(userService.getCurrentUser());
        } catch (IllegalArgumentException ex) {
            return getAccessPermissions(null);
        }
    }

    private Map<AccessOperation, Boolean> getAccessPermissions(User user) {
        Map<AccessOperation, Boolean> permissions = new HashMap<>();
        permissions.put(AccessOperation.ADD_NEW_POST, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_POSTER")));
        permissions.put(AccessOperation.EDIT_OWN_POST, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_POSTER")));
        permissions.put(AccessOperation.EDIT_ANY_POST, userService.userHasAuthority(user, "ROLE_ADMIN"));
        permissions.put(AccessOperation.DELETE_OWN_POST, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_POSTER")));
        permissions.put(AccessOperation.DELETE_ANY_POST, userService.userHasAuthority(user, "ROLE_ADMIN"));

        permissions.put(AccessOperation.ADD_NEW_COMMENT, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_COMMENTER")));
        permissions.put(AccessOperation.EDIT_OWN_COMMENT, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_COMMENTER")));
        permissions.put(AccessOperation.EDIT_ANY_COMMENT, userService.userHasAuthority(user, "ROLE_ADMIN"));
        permissions.put(AccessOperation.DELETE_OWN_COMMENT, userService.userHasAuthority(user, Set.of("ROLE_ADMIN", "ROLE_COMMENTER")));
        permissions.put(AccessOperation.DELETE_ANY_COMMENT, userService.userHasAuthority(user, "ROLE_ADMIN"));

        return permissions;
    }

    private void initPermissions() {
        permissions.put(AccessEntityOperation.OP_VIEW_POST,
                (o, user) -> {
                    return true;
                }
        );
        permissions.put(AccessEntityOperation.OP_INSERT_POST,
                (o, user) -> {
                    var ap = getAccessPermissions(user);
                    return ap.get(AccessOperation.ADD_NEW_POST);
                }
        );
        permissions.put(AccessEntityOperation.OP_EDIT_POST,
                (o, user) -> {
                    try {
                        var ap = getAccessPermissions(user);
                        if (o == null) {
                            throw new IllegalArgumentException("Post object must not be null");
                        }
                        Post post = (Post) o;
                        return (post.getAuthor().equals(user) && ap.get(AccessOperation.EDIT_OWN_POST)) || ap.get(AccessOperation.EDIT_ANY_POST);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                }
        );
        permissions.put(AccessEntityOperation.OP_DELETE_POST,
                (o, user) -> {
                    try {
                        var ap = getAccessPermissions(user);
                        if (o == null) {
                            throw new IllegalArgumentException("Post object must not be null");
                        }
                        Post post = (Post) o;
                        return (post.getAuthor().equals(user) && ap.get(AccessOperation.DELETE_OWN_POST)) || ap.get(AccessOperation.DELETE_ANY_POST);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                }
        );

        permissions.put(AccessEntityOperation.OP_VIEW_COMMENT,
                (o, user) -> {
                    return true;
                }
        );
        permissions.put(AccessEntityOperation.OP_INSERT_COMMENT,
                (o, user) -> {
                    var ap = getAccessPermissions(user);
                    return ap.get(AccessOperation.ADD_NEW_POST);
                }
        );
        permissions.put(AccessEntityOperation.OP_EDIT_COMMENT,
                (o, user) -> {
                    try {
                        var ap = getAccessPermissions(user);
                        if (o == null) {
                            throw new IllegalArgumentException("Comment object must not be null");
                        }
                        Comment comment = (Comment) o;
                        return (comment.getAuthor().equals(user) && ap.get(AccessOperation.EDIT_OWN_COMMENT)) || ap.get(AccessOperation.EDIT_ANY_COMMENT);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                }
        );
        permissions.put(AccessEntityOperation.OP_DELETE_COMMENT,
                (o, user) -> {
                    try {
                        var ap = getAccessPermissions(user);
                        if (o == null) {
                            throw new IllegalArgumentException("Comment object must not be null");
                        }
                        Comment comment = (Comment) o;
                        return (comment.getAuthor().equals(user) && ap.get(AccessOperation.DELETE_OWN_COMMENT)) || ap.get(AccessOperation.DELETE_ANY_COMMENT);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                }
        );
    }

    public boolean getPermission(AccessEntityOperation op, Object o, User user) {
        return permissions.get(op).apply(o, user);
    }

    public boolean getPermission(AccessEntityOperation op, Object o) {
        boolean result;
        try {
            result = getPermission(op, o, userService.getCurrentUser());
        } catch (IllegalArgumentException ex) {
            result = false;
        }
        return result;
    }

    public boolean getPermission(AccessEntityOperation op) {
        return getPermission(op, null);
    }
}

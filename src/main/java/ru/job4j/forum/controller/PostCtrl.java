package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.exception.AccessForbiddenException;
import ru.job4j.forum.exception.EntityNotFoundException;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.access.AccessEntityOperation;
import ru.job4j.forum.service.access.AccessOperation;
import ru.job4j.forum.service.access.AccessService;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

import java.util.*;

/**
 * The post controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
@Controller
@RequestMapping("/post")
public class PostCtrl {
    private final PostService posts;
    private final CommentService comments;
    private final UserService users;
    private final AccessService accessService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCtrl.class);

    public PostCtrl(PostService posts, CommentService comments, UserService users, AccessService accessService) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
        this.accessService = accessService;
    }

    @GetMapping("/{id:\\d+}")
    public String view(@PathVariable Integer id, Model model) throws EntityNotFoundException {
        try {
            Post item = posts.get(id);
            model.addAttribute("item", item);
            List<Comment> commentsOfPost = comments.getTreeByPost(id);
            model.addAttribute("comments", commentsOfPost);
            model.addAttribute("can_add_post", accessService.getPermission(AccessEntityOperation.OP_INSERT_POST));
            model.addAttribute("can_edit_post", accessService.getPermission(AccessEntityOperation.OP_EDIT_POST, item));
            model.addAttribute("can_delete_post", accessService.getPermission(AccessEntityOperation.OP_DELETE_POST, item));
            model.addAttribute("can_add_comment", accessService.getPermission(AccessEntityOperation.OP_INSERT_COMMENT));

            Map<Comment, Boolean> commentEditPermissions = new HashMap<>();
            Map<Comment, Boolean> commentDeletePermissions = new HashMap<>();
            commentsOfPost.forEach(comment -> {
                commentEditPermissions.put(comment, accessService.getPermission(AccessEntityOperation.OP_EDIT_COMMENT, comment));
                commentDeletePermissions.put(comment, accessService.getPermission(AccessEntityOperation.OP_DELETE_COMMENT, comment));
            });

            model.addAttribute("can_edit_comment", commentEditPermissions);
            model.addAttribute("can_delete_comment", commentDeletePermissions);

            return "post/view";
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a post with id = " + id);
        }
    }

    @GetMapping("/create")
    public String create(Model model) throws AccessForbiddenException {
        if (!accessService.getPermission(AccessEntityOperation.OP_INSERT_POST)) {
            throw new AccessForbiddenException("You can't enough rights to add new post");
        }
        model.addAttribute("item", new Post());
        model.addAttribute("can_add_post", accessService.getPermission(AccessEntityOperation.OP_INSERT_POST));
        model.addAttribute("title", "Add new post");
        return "post/edit";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model) throws EntityNotFoundException, AccessForbiddenException {
        try {
            Post item = posts.get(id);
            if (!accessService.getPermission(AccessEntityOperation.OP_EDIT_POST, item)) {
                throw new AccessForbiddenException("Editing of this post is forbidden");
            }
            model.addAttribute("can_add_post", accessService.getPermission(AccessEntityOperation.OP_INSERT_POST));
            model.addAttribute("item", item);
            return "post/edit";
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a post with id = " + id);
        }
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id, Model model) throws EntityNotFoundException, AccessForbiddenException {
        if (!accessService.getPermission(AccessEntityOperation.OP_DELETE_POST, posts.get(id))) {
            throw new AccessForbiddenException("Deleting of this post is forbidden");
        }
        posts.delete(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post item) {
        Calendar now = new GregorianCalendar();
        if (item.getId() == 0) {
            item.setCreated(now);
            item.setAuthor(users.getCurrentUser());
        } else {
            Post current = posts.get(item.getId());
            item.setCreated(current.getCreated());
            item.setAuthor(current.getAuthor());
        }
        item.setChanged(now);
        posts.save(item);
        return "redirect:/";
    }


}

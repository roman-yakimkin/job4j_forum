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
import ru.job4j.forum.service.access.AccessService;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The comment controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 */
@Controller
@RequestMapping("/comment")
public class CommentCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentCtrl.class);
    private final PostService posts;
    private final CommentService comments;
    private final UserService users;
    private final AccessService accessService;

    public CommentCtrl(PostService posts, CommentService comments, UserService users, AccessService accessService) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
        this.accessService = accessService;
    }

    @GetMapping({"/reply/{postId:\\d+}/{parentId:\\d+}", "/reply/{postId:\\d+}"})

    public String create(@PathVariable("postId") Integer postId,
                         @PathVariable(value = "parentId", required = false) Integer parentId, Model model) throws EntityNotFoundException, AccessForbiddenException {
        try {
            if (!accessService.getPermission(AccessEntityOperation.OP_INSERT_COMMENT)) {
                throw new AccessForbiddenException("You can't enough rights to add new comment");
            }
            parentId = parentId == null ? 0 : parentId;
            Post post = posts.get(postId);
            Comment parent = comments.get(parentId);
            if (parent == null || !parent.getPost().equals(post)) {
                throw new EntityNotFoundException("The post not found");
            }
            Comment item = new Comment();
            item.setId(0);
            item.setPost(post);
            item.setParent(parent);
            model.addAttribute("item", item);
            return "comment/edit";
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a post with id = " + postId);
        }
    }

    @GetMapping("/{commentId}/edit")
    public String edit(@PathVariable("commentId") Integer commentId, Model model) throws AccessForbiddenException, EntityNotFoundException {
        try {
            Comment item = comments.get(commentId);
            if (!accessService.getPermission(AccessEntityOperation.OP_EDIT_COMMENT, item)) {
                throw new AccessForbiddenException("You can't enough rights to edit this comment");
            }
            int parentId = item.getParent() != null ? item.getParent().getId() : 0;
            model.addAttribute("item", item);
            model.addAttribute("parentId", parentId);
            return "comment/edit";
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a comment with id = " + commentId);
        }
    }

    @GetMapping("/{commentId}/delete")
    public String delete(@PathVariable("commentId") Integer commentId, Model model) throws AccessForbiddenException, EntityNotFoundException {
        try {
            int postId = 0;
            Comment comment = comments.get(commentId);
            if (!accessService.getPermission(AccessEntityOperation.OP_EDIT_COMMENT, comment)) {
                throw new AccessForbiddenException("You can't enough rights to delete this comment");
            }
            postId = comment.getPost().getId();
            comments.delete(comment);
            return "redirect:/post/" + postId;
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a comment with id = " + commentId);
        }
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Comment item, HttpServletRequest req) {
        String path = "redirect:/";
        try {
            Calendar now = new GregorianCalendar();
            if (item.getId() == 0) {
                item.setCreated(now);
                item.setAuthor(users.getCurrentUser());
            } else {
                Comment current = comments.get(item.getId());
                item.setCreated(current.getCreated());
                item.setAuthor(current.getAuthor());
            }
            int postId = Integer.parseInt(req.getParameter("postId"));
            item.setPost(posts.get(postId));
            int parentId = Integer.parseInt(req.getParameter("parentId"));
            if (parentId != 0) {
                item.setParent(comments.get(parentId));
            }
            comments.save(item);
            path = "redirect:/post/" + postId;
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        return path;
    }
}
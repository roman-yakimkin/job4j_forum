package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.exception.EntityNotFoundException;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

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

    public CommentCtrl(PostService posts, CommentService comments, UserService users) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
    }

    @GetMapping({"/reply/{postId:\\d+}/{parentId:\\d+}", "/reply/{postId:\\d+}"})

    public String create(@PathVariable("postId") Integer postId,
                         @PathVariable(value = "parentId", required = false) Integer parentId, Model model) throws EntityNotFoundException {
        parentId = parentId == null ? 0 : parentId;
        Post post = posts.get(postId);
        if (post == null) {
            throw new EntityNotFoundException("Cannot find a post with id = " + postId);
        }
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
    }

    @GetMapping("/{commentId}/edit")
    public String edit(@PathVariable("commentId") Integer commentId, Model model) throws EntityNotFoundException {
        Comment item = comments.get(commentId);
        if (item == null) {
            throw new EntityNotFoundException("Cannot find a comment with id = " + commentId);
        }
        int parentId = item.getParent() != null ? item.getParent().getId() : 0;
        model.addAttribute("item", item);
        model.addAttribute("parentId", parentId);

        return "comment/edit";
    }

    @GetMapping("/{commentId}/delete")
    public String delete(@PathVariable("commentId") Integer commentId, Model model) throws EntityNotFoundException {
        int postId = 0;
        String path = "redirect:/";
        Comment comment = comments.get(commentId);
        if (comment != null) {
            postId = comment.getPost().getId();
            path = "redirect:/post/" + postId;
            comments.delete(commentId);
        } else {
            throw new EntityNotFoundException("Cannot find a comment with id = " + commentId);
        }
        return path;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Comment item, HttpServletRequest req) {
        String path = "redirect:/";
        try {
            Calendar now = new GregorianCalendar();
            if (item.getId() == 0) {
                item.setId(Comment.newId());
                item.setCreated(now);
                item.setAuthor(users.getCurrentUser());
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

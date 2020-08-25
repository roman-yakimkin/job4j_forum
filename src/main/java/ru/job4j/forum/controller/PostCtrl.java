package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.exception.EntityNotFoundException;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCtrl.class);

    public PostCtrl(PostService posts, CommentService comments, UserService users) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
    }

    @GetMapping("/{id:\\d+}")
    public String view(@PathVariable Integer id, Model model) throws EntityNotFoundException {
        String path = "post/view";
//        try {
//            int itemId = Integer.parseInt(id);
            Post item = posts.get(id);
            if (item == null) {
                throw new EntityNotFoundException("Cannot find a post with id = " + id);
            }
            model.addAttribute("item", posts.get(id));
            model.addAttribute("comments", comments.getTreeByPost(id));
//        }  catch (Exception ex) {
//            LOGGER.debug(ex.getMessage());
//            path = "redirect:/";
//        }
        return path;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("item", new Post());
        model.addAttribute("title", "Add new post");
        return "post/edit";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable String id, Model model) throws EntityNotFoundException {
        String path = "post/edit";
        try {
            int itemId = Integer.parseInt(id);
            Post item = posts.get(itemId);
            if (item == null) {
                throw new EntityNotFoundException("Cannot find a post with id = " + id);
            }
            model.addAttribute("item", posts.get(itemId));
        } catch (NumberFormatException ex) {
            LOGGER.debug(ex.getMessage());
            path = "redirect:/";
        }
        return path;
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable String id, Model model) {
        try {
            int itemId = Integer.parseInt(id);
            posts.delete(itemId);
        } catch (NumberFormatException ex) {
            LOGGER.debug(ex.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post item) {
        Calendar now = new GregorianCalendar();
        if (item.getId() == 0) {
            item.setId(Post.newId());
            item.setCreated(now);
            item.setAuthor(users.getCurrentUser());
        }
        item.setChanged(now);
        posts.save(item);
        return "redirect:/";
    }


}

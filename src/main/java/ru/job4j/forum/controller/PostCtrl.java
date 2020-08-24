package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    public PostCtrl(PostService posts, CommentService comments, UserService users) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        String path = "post/view";
        try {
            int itemId = Integer.parseInt(id);
            Post item = posts.get(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Cannot find a post with id = " + itemId);
            }
            model.addAttribute("item", posts.get(itemId));
            model.addAttribute("comments", comments.getTreeByPost(itemId));
        } catch (Exception ex) {
            ex.printStackTrace();
            path = "redirect:/";
        }
        return path;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("item", new Post());
        model.addAttribute("title", "Add new post");
        return "post/edit";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        String path = "post/edit";
        try {
            int itemId = Integer.parseInt(id);
            model.addAttribute("item", posts.get(itemId));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            path = "redirect:/";
        }
        return path;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id, Model model) {
        try {
            int itemId = Integer.parseInt(id);
            posts.delete(itemId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
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

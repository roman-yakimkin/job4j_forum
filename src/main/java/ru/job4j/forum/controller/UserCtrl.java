package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.exception.EntityNotFoundException;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * The user controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 24.08.2020
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserCtrl {
    private final UserService users;
    private final PostService posts;
    private final CommentService comments;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCtrl.class);

    public UserCtrl(UserService users, PostService posts, CommentService comments) {
        this.users = users;
        this.posts = posts;
        this.comments = comments;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable(value = "id") Integer id, Model model) throws EntityNotFoundException {
        User item = users.get(id);
        if (item == null) {
            throw new EntityNotFoundException("Cannot find a user with id = " + id);
        }
        model.addAttribute("item", item);
        model.addAttribute("posts", posts.getLatestByUser(item, 10));
        model.addAttribute("comments", comments.getLatestByUser(item, 10));
        return "user/view";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user, HttpServletRequest req) {
        return "user/login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute User user, HttpServletRequest req) {
        return "redirect:/";
    }

}

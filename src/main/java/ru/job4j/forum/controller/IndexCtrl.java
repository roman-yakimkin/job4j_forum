package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

import java.util.List;

/**
 * The index controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
@Controller
public class IndexCtrl {
    private final PostService posts;
    private final CommentService comments;
    private final UserService users;

    public IndexCtrl(PostService posts, CommentService comments, UserService users) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("posts", posts.getAll());
        model.addAttribute("user", users.getCurrentUser());
        return "index";
    }
}

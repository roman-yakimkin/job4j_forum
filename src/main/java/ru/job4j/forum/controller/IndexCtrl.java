package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.forum.service.access.AccessEntityOperation;
import ru.job4j.forum.service.access.AccessOperation;
import ru.job4j.forum.service.access.AccessService;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

/**
 * The index controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
@Controller
public class IndexCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexCtrl.class);
    private final PostService posts;
    private final CommentService comments;
    private final UserService users;
    private final AccessService accessService;

    public IndexCtrl(PostService posts, CommentService comments, UserService users, AccessService accessService) {
        this.posts = posts;
        this.comments = comments;
        this.users = users;
        this.accessService = accessService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("posts", posts.getLatest());
        model.addAttribute("can_add_post", accessService.getPermission(AccessEntityOperation.OP_INSERT_POST));
        return "index";
    }
}

package ru.job4j.forum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.forum.exception.EntityNotFoundException;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.jpa.AuthorityService;
import ru.job4j.forum.service.jpa.CommentService;
import ru.job4j.forum.service.jpa.PostService;
import ru.job4j.forum.service.jpa.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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
    private final PasswordEncoder encoder;
    private final AuthorityService authorities;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCtrl.class);

    public UserCtrl(UserService users, PostService posts, CommentService comments, PasswordEncoder encoder, AuthorityService authorities) {
        this.users = users;
        this.posts = posts;
        this.comments = comments;
        this.encoder = encoder;
        this.authorities = authorities;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable(value = "id") Integer id, Model model) throws EntityNotFoundException {
        try {
            User item = users.get(id);
            model.addAttribute("item", item);
            model.addAttribute("posts", posts.getLatestByUser(item, 10));
            model.addAttribute("comments", comments.getLatestByUser(item, 10));
            return "user/view";
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Cannot find a user with id = " + id);
        }
    }

    @GetMapping("/register")
    public String register(@ModelAttribute User user) {
        return "user/register";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        String errorMsg = null;
        if (error != null) {
            errorMsg = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMsg = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMsg", errorMsg);
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, resp, auth);
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user, HttpServletRequest req) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(authorities.getOne("ROLE_POSTER"), authorities.getOne("ROLE_COMMENTER")));
        users.save(user);
        return "redirect:/user/login";
    }

//    @PostMapping("/login")
//    public String doLogin(@ModelAttribute User user, HttpServletRequest req) {
//        return "redirect:/";
//    }

}

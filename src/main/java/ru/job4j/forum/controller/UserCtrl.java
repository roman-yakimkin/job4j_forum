package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.forum.model.User;

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

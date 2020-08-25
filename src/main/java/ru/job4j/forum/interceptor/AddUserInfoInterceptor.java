package ru.job4j.forum.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.forum.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interceptor for adding user info in controller methods
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 25.08.2020
 * @version 1.0
 */
@Component
public class AddUserInfoInterceptor implements HandlerInterceptor {

    private final UserService users;

    public AddUserInfoInterceptor(UserService users) {
        this.users = users;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getMethod().equals("GET") && modelAndView != null) {
            modelAndView.addObject("user", users.getCurrentUser());
        }
    }
}

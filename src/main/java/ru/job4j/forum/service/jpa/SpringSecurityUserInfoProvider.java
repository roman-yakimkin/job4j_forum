package ru.job4j.forum.service.jpa;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.job4j.forum.service.UserInfoProvider;

/**
 * The user info provider via Spring Security
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 03.09.2020
 * @version 1.0
 */
@Component
public class SpringSecurityUserInfoProvider implements UserInfoProvider {
    @Override
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }
}

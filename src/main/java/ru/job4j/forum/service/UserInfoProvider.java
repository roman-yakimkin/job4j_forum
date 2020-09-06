package ru.job4j.forum.service;

import org.springframework.context.annotation.Bean;

/**
 * The user info provider class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 03.09.2020
 * @version 1.0
 */
public interface UserInfoProvider {
    public String getUserName();
}

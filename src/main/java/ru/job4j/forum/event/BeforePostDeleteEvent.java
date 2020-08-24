package ru.job4j.forum.event;

import org.springframework.context.ApplicationEvent;
import ru.job4j.forum.model.Post;

/**
 * The before post delete event
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 24.08.2020
 * @version 1.0
 */
public class BeforePostDeleteEvent extends ApplicationEvent {

    private Post post;

    public BeforePostDeleteEvent(Object source, Post post) {
        super(source);
        this.post = post;
    }

    public Post getPost() {
        return this.post;
    }
}

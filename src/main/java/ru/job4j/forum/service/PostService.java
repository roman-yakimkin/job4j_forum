package ru.job4j.forum.service;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.job4j.forum.event.BeforePostDeleteEvent;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.*;

/**
 * The post service class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
@Service("PostService")
public class PostService {
    private Map<Integer, Post> posts = new HashMap<>();
    private ApplicationEventPublisher eventPublisher;

    public PostService(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;

        User author1 = userService.getByName("root");
        User author2 = userService.getByName("petrov");

        LoremIpsum loremIpsum = new LoremIpsum();

        Post first = Post.of("A question about data migration from с Wordpress to Java", loremIpsum.getWords(100), author1);
        Post second = Post.of("The future of Drupal 9", loremIpsum.getWords(30, 2), author2);
        Post third = Post.of("Laravel or Symfony", loremIpsum.getParagraphs(2), author1);

        posts.putAll(Map.of(
                first.getId(), first,
                second.getId(), second,
                third.getId(), third
        ));
    }

    public Post get(int id) {
        return posts.get(id);
    }

    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

    public void delete(int id) {
        Post post = posts.get(id);
        if (post != null) {
            eventPublisher.publishEvent(new BeforePostDeleteEvent(this, post));
        }
        posts.remove(id);
    }

    public void save(Post post) {
        posts.put(post.getId(), post);
    }
}

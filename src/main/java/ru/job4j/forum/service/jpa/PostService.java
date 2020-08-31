package ru.job4j.forum.service.jpa;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.job4j.forum.comparator.SortByTimeDescComparator;
import ru.job4j.forum.event.BeforePostDeleteEvent;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.PostRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The post service class for JPA
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 30.08.2020
 * @version 1.0
 */
@Service("JpaPostService")
public class PostService {
    private final PostRepository posts;
    private ApplicationEventPublisher eventPublisher;

    public PostService(PostRepository posts, ApplicationEventPublisher eventPublisher) {
        this.posts = posts;
        this.eventPublisher = eventPublisher;
    }

    public Post get(int id) {
        return posts.findById(id).orElse(null);
    }

    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        posts.findAll().forEach(result::add);
        return result;
    }

    public List<Post> getLatest() {
        return posts.findByOrderByChangedDesc();
    }

    public void delete(int id) {
        Post post = get(id);
        if (post != null) {
            eventPublisher.publishEvent(new BeforePostDeleteEvent(this, post));
        }
        posts.deleteById(id);
    }

    public List<Post> getLatestByUser(User user, int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("changed").descending());
        Page<Post> page = posts.findByAuthor(user, pageable);
        List<Post> result = new ArrayList<>();
        page.forEach(result::add);
        return result;
    }

    public void save(Post post) {
        posts.save(post);
    }
}

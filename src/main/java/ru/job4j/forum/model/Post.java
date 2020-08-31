package ru.job4j.forum.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The forum post class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
@Entity
@Table(name = "post")
public class Post implements TimeableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "created")
    private Calendar created;

    @Column(name = "changed")
    private Calendar changed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    private static int counter = 0;

    public static int newId() {
        return ++counter;
    }

    public static Post of(String title, String body, User author) {
        Post post = new Post();
//        post.id = Post.newId();
        post.title = title;
        post.body = body;
        post.author = author;
        Calendar now = new GregorianCalendar();
        post.setCreated(now);
        post.setChanged(now);
        return post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getChanged() {
        return changed;
    }

    public void setChanged(Calendar changed) {
        this.changed = changed;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

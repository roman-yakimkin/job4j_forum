package ru.job4j.forum.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The forum post class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 17.08.2020
 * @version 1.0
 */
public class Post implements TimeableEntity {
    private int id;
    private String title;
    private String body;
    private Calendar created;
    private Calendar changed;
    private User author;
    private List<Comment> comments = new ArrayList<>();

    private static int counter = 0;

    public static int newId() {
        return ++counter;
    }

    public static Post of(String title, String body, User author) {
        Post post = new Post();
        post.id = Post.newId();
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

    public List<Comment> getComments() {
        return comments;
    }

    public List<Comment> getDirectComments() {
        return comments.stream().filter((comment -> comment.getParent() == null)).collect(Collectors.toList());
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

package ru.job4j.forum.model;

import java.util.*;

/**
 * The comment entity class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
public class Comment implements TimeableEntity {
    private int id;
    private Calendar created;
    private Calendar changed;
    private User author;
    private String body;
    private Post post;
    private Comment parent;
    private int depth;

    private static int counter = 0;

    public static int newId() {
        return ++counter;
    }

    public static Comment of(Post post, Comment parent, User author, String body) {
        Comment comment = new Comment();
        comment.setId(Comment.newId());
        Calendar now = new GregorianCalendar();
        comment.setCreated(now);
        comment.setChanged(now);
        comment.setAuthor(author);
        comment.setBody(body);
        comment.setPost(post);
        comment.setParent(parent);
        comment.setDepth(comment.getParent() != null ? parent.getDepth() + 1 : 0);
        return comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

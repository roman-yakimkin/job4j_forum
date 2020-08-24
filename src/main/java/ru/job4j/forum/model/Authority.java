package ru.job4j.forum.model;

import java.util.Objects;

/**
 * The authority entity class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 18.08.2020
 * @version 1.0
 */
public class Authority {
    private String id;
    private String desc;

    public static Authority of(String id, String desc) {
        Authority authority = new Authority();
        authority.setId(id);
        authority.setDesc(desc);
        return authority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority = (Authority) o;
        return id.equals(authority.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

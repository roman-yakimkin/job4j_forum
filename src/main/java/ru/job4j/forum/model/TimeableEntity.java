package ru.job4j.forum.model;

import java.util.Calendar;

/**
 * An interface for entities contains time created and changed
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 25.08.2020
 * @version 1.0
 */
public interface TimeableEntity {
    Calendar getCreated();
    Calendar getChanged();
}

package ru.job4j.forum.service.access;

/**
 * The access operations enum
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 04.09.2020
 * @version 1.0
 */
public enum AccessEntityOperation {
    OP_VIEW_POST, OP_INSERT_POST, OP_EDIT_POST, OP_DELETE_POST,
    OP_VIEW_COMMENT, OP_INSERT_COMMENT, OP_EDIT_COMMENT, OP_DELETE_COMMENT,
}

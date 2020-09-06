package ru.job4j.forum.service.access;

/**
 * The access operation enumeration
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 04.09.2020
 * @version 1.0
 */
public enum AccessOperation {
    ADD_NEW_COMMENT,
    EDIT_OWN_COMMENT,
    EDIT_ANY_COMMENT,
    DELETE_OWN_COMMENT,
    DELETE_ANY_COMMENT,
    ADD_NEW_POST,
    EDIT_OWN_POST,
    EDIT_ANY_POST,
    DELETE_OWN_POST,
    DELETE_ANY_POST
}

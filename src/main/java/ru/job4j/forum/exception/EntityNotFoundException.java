package ru.job4j.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}

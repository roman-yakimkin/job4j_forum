package ru.job4j.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You don't have enough rights to access into this page")
public class AccessForbiddenException extends Exception {
    public AccessForbiddenException(String message) {
        super(message);
    }
}

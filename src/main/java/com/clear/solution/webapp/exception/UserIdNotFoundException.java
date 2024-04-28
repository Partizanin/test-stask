package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserIdNotFoundException extends BaseMessageException {
    public UserIdNotFoundException(Long id) {
        super(String.format("User with id:%s not found", id));
    }
}

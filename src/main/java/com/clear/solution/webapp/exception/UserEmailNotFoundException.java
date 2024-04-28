package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserEmailNotFoundException extends BaseMessageException {
    public UserEmailNotFoundException(String email) {
        super(String.format("User with email:%s not found", email));
    }
}

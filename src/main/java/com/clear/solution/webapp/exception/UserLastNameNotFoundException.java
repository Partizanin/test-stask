package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserLastNameNotFoundException extends BaseMessageException {
    public UserLastNameNotFoundException(String lastName) {
        super(String.format("User with last name:%s not found", lastName));
    }
}

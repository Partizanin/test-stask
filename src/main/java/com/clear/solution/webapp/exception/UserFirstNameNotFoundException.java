package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserFirstNameNotFoundException extends BaseMessageException {
    public UserFirstNameNotFoundException(String firstName) {
        super(String.format("User first name:%s not found", firstName));
    }
}

package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRequiredAgeException extends BaseMessageException {
    public UserRequiredAgeException(Integer minYearsRequired) {
        super(String.format("User age should be:%s or higher", minYearsRequired));
    }
}

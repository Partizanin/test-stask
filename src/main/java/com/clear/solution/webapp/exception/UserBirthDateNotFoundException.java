package com.clear.solution.webapp.exception;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserBirthDateNotFoundException extends BaseMessageException {
    public UserBirthDateNotFoundException(LocalDate birthDate) {
        super(String.format("User birth date %s not found.", birthDate));
    }
}

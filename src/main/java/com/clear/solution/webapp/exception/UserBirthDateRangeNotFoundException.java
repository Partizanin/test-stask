package com.clear.solution.webapp.exception;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserBirthDateRangeNotFoundException extends BaseMessageException {
    public UserBirthDateRangeNotFoundException(LocalDate from, LocalDate to) {
        super(String.format("Users between %s  and %s not found", from, to));
    }
}

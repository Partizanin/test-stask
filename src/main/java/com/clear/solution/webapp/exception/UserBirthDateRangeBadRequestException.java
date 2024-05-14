package com.clear.solution.webapp.exception;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserBirthDateRangeBadRequestException extends BaseMessageException {
    public UserBirthDateRangeBadRequestException(LocalDate from, LocalDate to) {
        super(String.format("User date from:%s should be less tan to:%s ", from, to));
    }
}

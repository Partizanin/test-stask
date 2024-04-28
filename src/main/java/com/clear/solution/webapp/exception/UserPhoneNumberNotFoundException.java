package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserPhoneNumberNotFoundException extends BaseMessageException {
    public UserPhoneNumberNotFoundException(String phoneNumber) {
        super(String.format("User phone number:%s not found", phoneNumber));
    }
}

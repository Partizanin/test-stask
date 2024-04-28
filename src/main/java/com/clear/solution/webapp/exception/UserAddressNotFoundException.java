package com.clear.solution.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAddressNotFoundException extends BaseMessageException {
    public UserAddressNotFoundException(String address) {
        super(String.format("User address:%s not found", address));
    }
}

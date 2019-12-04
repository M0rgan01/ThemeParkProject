package com.theme.park.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotMatchException extends AuthenticationException {
    public UserNotMatchException(String msg) {
        super(msg);
    }
}

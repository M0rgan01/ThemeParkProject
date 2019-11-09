package com.theme.park.exception;

import org.springframework.security.core.AuthenticationException;

public class JsonException extends AuthenticationException {
    public JsonException(String msg) {
        super(msg);
    }
}

package com.theme.park.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 */

public class AuthMethodNotSupportedException extends AuthenticationException {
    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}

package com.theme.park.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 */
public class JwtExpiredException extends AuthenticationException {


    public JwtExpiredException(String msg) {
        super(msg);
    }

    public JwtExpiredException(String msg, Throwable t) {
        super(msg, t);
    
    }

}

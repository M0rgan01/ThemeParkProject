package com.theme.park.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 */
public class JwtExpiredTokenException extends AuthenticationException {


    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    
    }

}

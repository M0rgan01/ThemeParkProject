package com.theme.park.security.auth.jwt;


import com.theme.park.utilities.token.JwtAuthenticationToken;
import com.theme.park.utilities.token.JwtService;
import com.theme.park.utilities.token.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre de mise en place d'authentification par JWT 
 * 
 * 
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 */
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationProcessingFilter.class);
    private final AuthenticationFailureHandler failureHandler;
    private String accessTokenPrefix;
    private JwtService jwtService;

    public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
                                                  RequestMatcher matcher,
                                                  String accessTokenPrefix,
                                                  JwtService jwtService) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.accessTokenPrefix = accessTokenPrefix;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // get token from a Cookie
        Cookie[] cookies = request.getCookies();

        Cookie sessionCookie = jwtService.findToken(cookies, accessTokenPrefix);

    	//on récupère le token
         logger.debug("Getting token");
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(new JwtToken(sessionCookie.getValue())));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}

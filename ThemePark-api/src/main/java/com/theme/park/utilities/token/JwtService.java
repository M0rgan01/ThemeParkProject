package com.theme.park.utilities.token;


import com.theme.park.entities.SocialUser;
import com.theme.park.exception.JwtExpiredException;
import com.theme.park.exception.JwtInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Création, modification, et vérification de JWT
 *
 * @author pichat morgan
 * <p>
 * 20 Juillet 2019
 */
public interface JwtService {

    /**
     * Création d'un token d'authentification
     *
     * @param userContext --> pour les informations à injecté dans le token (username, liste de role)
     * @return token
     */
    String createAuthToken(SocialUser userContext) throws IllegalArgumentException;

    /**
     * Création d'un token de rafraichissement
     *
     * @param userContext --> pour les informations à injecté dans le token (username)
     * @return token
     */
    String createRefreshToken(SocialUser userContext) throws IllegalArgumentException;

    /**
     * Récupère un utilisateur grace a un token
     *
     * @param token --> token à vérifier
     * @return claims
     */
    SocialUser getUserWithToken(JwtToken token) throws AuthenticationException;
    SocialUser getUserWithAccessCookie(HttpServletRequest httpServletRequest) throws AuthenticationException;

    /**
     * Parses and validates JWT Token signature.
     *
     * @throws JwtInvalidException
     * @throws JwtExpiredException
     */
    Jws<Claims> parseClaims(String token) throws AuthenticationException;

    Cookie findToken(Cookie[] cookies, String tokenType) throws BadCredentialsException;

    Cookie createAccessTokenCookie(String accessToken);

    Cookie createRefreshTokenCookie(String accessToken);

    Cookie resetAccessTokenCookie();

    Cookie resetRefreshTokenCookie();
}

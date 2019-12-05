package com.theme.park.utilities.token;

import com.theme.park.business.SocialUserBusiness;
import com.theme.park.entities.Role;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.JwtExpiredException;
import com.theme.park.exception.JwtInvalidException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author pichat morgan
 * <p>
 * 20 Juillet 2019
 */
@Service
public class JwtServiceImpl implements JwtService {

    private SocialUserBusiness socialUserBusiness;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationProcessingFilter.class);

    public JwtServiceImpl(SocialUserBusiness socialUserBusiness) {
        this.socialUserBusiness = socialUserBusiness;
    }

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration.token.auth}")
    private Long authTokenExpiration;
    @Value("${jwt.expiration.token.refresh}")
    private Long refreshTokenExpiration;
    @Value("${jwt.prefix.authorities}")
    private String authoritiesPrefix;
    @Value("${jwt.prefix.provider}")
    private String providerPrefix;
    @Value("${jwt.refresh.prefix}")
    private String refreshTokenPrefix;
    @Value("${jwt.access.prefix}")
    private String accessTokenPrefix;

    @Override
    public String createAuthToken(SocialUser userContext) throws IllegalArgumentException {

        logger.debug("Create authToken for userName " + userContext.getName());
        // construction du Json web token
        return Jwts.builder().setSubject(userContext.getEmail()) // ajout du mail
                .setExpiration(new Date(System.currentTimeMillis() + authTokenExpiration)) // ajout d'une date d'expiration
                .signWith(SignatureAlgorithm.HS256, secret) // partie secrete servant de clé, avec un algorithme de type HS 256
                .claim(providerPrefix, userContext.getProvider()) // ajout du provider
                .claim(authoritiesPrefix, userContext.getRoles().stream().map(s -> s.toString()).collect(Collectors.toList())) // ajout personnalisé --> on ajoute les roles
                .compact(); // construction du token
    }

    @Override
    public String createRefreshToken(SocialUser userContext) throws IllegalArgumentException {

        logger.debug("Create refreshToken for email " + userContext.getName() + " and provider " + userContext.getProvider());
        // construction du Json web token
        return Jwts.builder().setSubject(userContext.getEmail()) // ajout du mail
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // ajout d'une date d'expiration
                .signWith(SignatureAlgorithm.HS256, secret) // partie secrete servant de clé, avec un algorithme de type HS 256
                .claim(providerPrefix, userContext.getProvider()) // ajout du provider
                .compact(); // construction du token
    }

    @Override
    public SocialUser getUserWithToken(JwtToken token) throws AuthenticationException {

        // vérification du token
        Jws<Claims> claims = parseClaims(token.getToken());

        try {
            // on recupere le contact pour comparaison
            SocialUser contact = socialUserBusiness.getSocialUserByEmail(claims.getBody().getSubject(), claims.getBody().get(providerPrefix, String.class));
            // si le contact est toujours bon, alors le token est toujours valide
            if (!contact.isActive())
                throw new DisabledException("user.not.active");

            contact.setAuthorities(Role.getListAuthorities(contact.getRoles()));
            logger.debug("Validate refreshToken for userName " + contact.getName());
            return contact;
        } catch (NotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("user.not.found");
        }
    }

    @Override
    public SocialUser getUserWithAccessCookie(HttpServletRequest request) throws AuthenticationException {
        Cookie accessCookie = findToken(request.getCookies(), accessTokenPrefix);
        return getUserWithToken(new JwtToken(accessCookie.getValue()));
    }

    @Override
    public Jws<Claims> parseClaims(String token) throws AuthenticationException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new JwtInvalidException("jwt.invalid");
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredException("jwt.expired");
        }
    }

    @Override
    public Cookie findToken(Cookie[] cookies, String tokenType) throws BadCredentialsException {

        Cookie sessionCookie = null;

        if( cookies == null || cookies.length < 1 ) {
            throw new JwtInvalidException("jwt.invalid");
        }

        for( Cookie cookie : cookies ) {
            if( (tokenType).equals( cookie.getName() ) ) {
                sessionCookie = cookie;
                break;
            }
        }

        if( sessionCookie == null || StringUtils.isEmpty( sessionCookie.getValue() ) ) {
            throw new JwtInvalidException("jwt.invalid");
        }

        return sessionCookie;
    }

    @Override
    public Cookie createAccessTokenCookie(String accessToken){
        Cookie accessCookie = new Cookie(accessTokenPrefix, accessToken);
        accessCookie.setPath("/themeParkAPI");
        accessCookie.setHttpOnly(true);
        accessCookie.setMaxAge(604800); // 7 Jours
        return accessCookie;
    }

    @Override
    public Cookie createRefreshTokenCookie(String refreshToken){
        Cookie refreshCookie = new Cookie(refreshTokenPrefix, refreshToken);
        refreshCookie.setPath("/themeParkAPI/auth/refresh");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(864000); // 10 Jours
        return refreshCookie;
    }

    @Override
    public Cookie resetAccessTokenCookie() {
        Cookie accessCookie = new Cookie(accessTokenPrefix, null);
        accessCookie.setPath("/themeParkAPI");
        accessCookie.setHttpOnly(true);
        accessCookie.setMaxAge(0);
        return accessCookie;
    }

    @Override
    public Cookie resetRefreshTokenCookie() {
        Cookie refreshCookie = new Cookie(refreshTokenPrefix, null);
        refreshCookie.setPath("/themeParkAPI/auth/refresh");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(0);
        return refreshCookie;
    }

}

package com.theme.park.security.token;


import com.theme.park.exception.JwtExpiredTokenException;
import com.theme.park.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.theme.park.doa.SocialUserRepository;
import com.theme.park.entities.Role;
import com.theme.park.entities.SocialUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pichat morgan
 *
 * 20 Juillet 2019
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private SocialUserRepository socialUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationProcessingFilter.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration.token.auth}")
    private Long authTokenExpiration;
    @Value("${jwt.expiration.token.refresh}")
    private Long refreshTokenExpiration;
    @Value("${jwt.prefix}")
    private String tokenPrefix;
    @Value("${jwt.prefix.authorities}")
    private String authoritiesPrefix;
    @Value("${jwt.prefix.provider}")
    private String providerPrefix;
    @Value("${jwt.prefix.active.refresh}")
    private String activePrefix;

    @Override
    public String createAuthToken(SocialUser userContext) throws IllegalArgumentException {
        if (userContext.getName() == null || userContext.getName().isEmpty())
            throw new IllegalArgumentException("jwt.auth.username.null");

        if (userContext.getRoles() == null || userContext.getRoles().isEmpty())
            throw new IllegalArgumentException("jwt.auth.authorities.null");

        logger.debug("Create authToken for userName " + userContext.getName());
        // construction du Json web token
        return Jwts.builder().setSubject(userContext.getName()) // ajout de username
                .setExpiration(new Date(System.currentTimeMillis() + authTokenExpiration)) // ajout d'une date d'expiration
                .signWith(SignatureAlgorithm.HS256, secret) // partie secrete servant de clé, avec un algorithme de type HS 256
                .claim(providerPrefix, userContext.getProvider())
                .claim(authoritiesPrefix, userContext.getRoles().stream().map(s -> s.toString()).collect(Collectors.toList())) // ajout personnalisé --> on ajoute les roles
                .compact(); // construction du token
    }

    @Override
    public String createRefreshToken(SocialUser userContext) throws IllegalArgumentException{

        if (userContext.getName() == null || userContext.getName().isEmpty())
            throw new IllegalArgumentException("jwt.auth.username.null");


        logger.debug("Create refreshToken for userName " + userContext.getName());
        // construction du Json web token
        return Jwts.builder().setSubject(userContext.getName()) // ajout de username
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // ajout d'une date d'expiration
                .signWith(SignatureAlgorithm.HS256, secret) // partie secrete servant de clé, avec un algorithme de type HS 256
                .claim(providerPrefix, userContext.getProvider())
                .claim(activePrefix, true) // ajout de vérification pour le rafraichissement
                .compact(); // construction du token

    }

    @Override
    public SocialUser validateRefreshToken(JwtToken token) throws AuthenticationException {

        // vérification du token
        Jws<Claims> claims = parseClaims(token.getToken());

        // on recupere le contact pour comparaison
        Optional<SocialUser> contact = socialUserRepository.findByNameAndProvider(claims.getBody().getSubject(), claims.getBody().get(providerPrefix, String.class));

        if (!contact.isPresent())
            throw new BadCredentialsException("user.not.found");
        // si le contact est toujours bon, alors le token est toujours valide
        else if (contact.get().isActive() != claims.getBody().get(activePrefix, Boolean.class))
            throw new DisabledException("user.not.active");

        contact.get().setAuthorities(Role.getListAuthorities(contact.get().getRoles()));
        logger.debug("Validate refreshToken for userName " + contact.get().getName());
        return contact.get();
    }

    @Override
    public String extract(String header) throws BadCredentialsException {

        if (header == null || header.isEmpty()) {
            throw new BadCredentialsException("authorization.header.blank");
        }

        return header.substring(tokenPrefix.length(), header.length());
    }

    @Override
    public Jws<Claims> parseClaims(String token) throws AuthenticationException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("jwt.invalid");
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredTokenException( "jwt.expired");
        }
    }

}

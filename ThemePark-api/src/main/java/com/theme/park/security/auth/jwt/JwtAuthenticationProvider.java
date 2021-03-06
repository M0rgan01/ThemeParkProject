package com.theme.park.security.auth.jwt;


import com.theme.park.entities.Role;
import com.theme.park.entities.SocialUser;
import com.theme.park.utilities.token.JwtAuthenticationToken;
import com.theme.park.utilities.token.JwtService;
import com.theme.park.utilities.token.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Processus d'authentification par JWT 
 * 
 * 
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationProcessingFilter.class);
    @Value("${jwt.prefix.authorities}")
    private String authoritiesPrefix;
    @Value("${jwt.prefix.provider}")
    private String providerPrefix;
    private JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        JwtToken jwtToken = (JwtToken) authentication.getCredentials();

        //on récupère les claimes, tout en vérifiant le token
        Jws<Claims> jwsClaims = jwtService.parseClaims(jwtToken.getToken());
        
        // récupère le sujet (username)
        String subject = jwsClaims.getBody().getSubject();
        
        @SuppressWarnings("unchecked")
		List<String> listRoles = jwsClaims.getBody().get(authoritiesPrefix, List.class);

        //création d'un utilisateur grace au nom à la liste de role contenu dans le token
        SocialUser context = new SocialUser();
        context.setEmail(subject);
        context.setAuthorities(Role.getListAuthorities(listRoles));
        logger.debug("Success authentication for user email " + subject + " and provider : " + jwsClaims.getBody().get(providerPrefix, String.class));
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

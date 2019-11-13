package com.theme.park.security.auth.login;

import com.theme.park.business.SocialUserBusiness;
import com.theme.park.entities.Role;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.SocialUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 * Processus d'authentification par login
 *
 * @author Pichat morgan
 * <p>
 * 20 Juillet 2019
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private SocialUserBusiness socialUserBusiness;
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    @Autowired
    public LoginAuthenticationProvider(SocialUserBusiness socialUserBusiness) {
        this.socialUserBusiness = socialUserBusiness;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // récupération des information de connection
        SocialUserDTO socialUserDTO = (SocialUserDTO) authentication.getPrincipal();
        SocialUser socialUser;

        // on vérifie la présence d'un utilisateur semblable en DB, sinon on l'inscrit
       try {
         socialUser = socialUserBusiness.getSocialUserByEmail(socialUserDTO.getEmail(), socialUserDTO.getProvider());
       } catch (NotFoundException e) {
           try {
               socialUser = socialUserBusiness.createSocialUser(socialUserDTO);
           } catch (NotFoundException e1) {
               logger.error("Role not found");
               throw new AuthenticationServiceException("internal.error");
           } catch (AlreadyExistException e2) {
               logger.error("User already exist with email " + socialUserDTO.getEmail() + " and provider " + socialUserDTO.getProvider());
               throw new AuthenticationServiceException("internal.error");
           }
       }

        // vérification des roles
        if (socialUser.getRoles() == null){
            logger.warn("User role null for userName : " + socialUser.getName());
            throw new InsufficientAuthenticationException("user.roles.null");
        }
        socialUser.setAuthorities(Role.getListAuthorities(socialUser.getRoles()));
        logger.debug("Success authentication for userName : " + socialUser.getName());

        return new UsernamePasswordAuthenticationToken(socialUser, null, socialUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

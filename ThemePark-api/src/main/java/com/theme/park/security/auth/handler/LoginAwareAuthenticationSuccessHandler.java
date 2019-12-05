package com.theme.park.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.entities.SocialUser;
import com.theme.park.object.SocialUserDTO;
import com.theme.park.utilities.token.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 
 * Processus de succès de connection personnalisé
 * 
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 * 
 */
@Component
public class LoginAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private JwtService jwtService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public LoginAwareAuthenticationSuccessHandler(JwtService jwtService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SocialUser userContext = (SocialUser) authentication.getPrincipal();
        //création des token
        String accessToken = jwtService.createAuthToken(userContext);
        String refreshToken = jwtService.createRefreshToken(userContext);

        ////// ACCESS TOKEN  //////
        response.addCookie(jwtService.createAccessTokenCookie(accessToken));

        ////// REFRESH TOKEN  //////
        response.addCookie(jwtService.createRefreshTokenCookie(refreshToken));

        // on met en place le code de la réponse
        response.setStatus(HttpStatus.OK.value());
        // on ajoute l'entité user
        response.getWriter().write(objectMapper.writeValueAsString(modelMapper.map(userContext, SocialUserDTO.class)));
        clearAuthenticationAttributes(request);

    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}

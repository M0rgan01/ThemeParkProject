package com.theme.park.security.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.exception.AuthMethodNotSupportedException;
import com.theme.park.exception.JsonException;
import com.theme.park.object.SocialUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

/**
 * Filtre de mise en place d'authentification par Login
 *
 * @author Pichat morgan
 * <p>
 * 20 Juillet 2019
 */
public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginProcessingFilter.class);
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper objectMapper;

    public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                 AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // vérification que la méthode soit bien POST
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            logger.info("Method not supported");
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        //récupération du json contenu dans le corp de requete
        SocialUserDTO contact;

        try {
            contact = objectMapper.readValue(request.getInputStream(), SocialUserDTO.class);
        } catch (Exception e) {
            logger.info("Json error for SocialUser object");
            throw new JsonException("json.error");
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SocialUserDTO>> violations = validator.validate(contact);

        for (ConstraintViolation<SocialUserDTO> violation : violations) {
            throw new InsufficientAuthenticationException(violation.getMessage());
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(contact, null);
        //processus d'authentification

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}

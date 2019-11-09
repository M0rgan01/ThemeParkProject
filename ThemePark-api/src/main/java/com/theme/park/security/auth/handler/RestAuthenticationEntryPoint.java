package com.theme.park.security.auth.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * Processus d'accès refusé personnalisé
 * 
 * @author Pichat morgan
 *
 * 20 Juillet 2019
 * 
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
		
		//mise au point du code de status et du contenu de la réponse
		response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");

	}
}

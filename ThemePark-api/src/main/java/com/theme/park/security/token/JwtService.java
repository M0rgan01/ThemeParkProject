package com.theme.park.security.token;


import com.theme.park.entities.SocialUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

/**
 * Création, modification, et vérification de JWT
 * 
 * 
 * @author pichat morgan
 *
 * 20 Juillet 2019
 *
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
  * Vérification de conformité d'un refresh token
  * 
  * @param token --> token à vérifier
  * @return claims
  */
 SocialUser validateRefreshToken(JwtToken token)throws AuthenticationException;
 
 /**
  * Retire le préfixe précédent le token
  * 
  * @param header --> header complet du token (avec préfix)
  * @return token (sans préfix)
  */
  String extract(String header) throws BadCredentialsException;
}

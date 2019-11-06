package com.theme.park.security.token;

import com.theme.park.exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;


/**
 * Représention d'un JWT pour l'authentification
 * 
 * @author pichat morgan
 *
 * 20 Juillet 2019
 *
 */
public class JwtToken {

	private String token;

	public JwtToken(String token) {
		super();
		this.token = token;
	}

	public String getToken() {		
		return this.token;
	}
	
	 /**
     * Parses and validates JWT Token signature.
     * 
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     * 
     */
    public Jws<Claims> parseClaims(String token, String secret) throws AuthenticationException {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("jwt.invalid");
        } catch (ExpiredJwtException expiredEx) {       
            throw new JwtExpiredTokenException( "jwt.expired");
        }
    }
    
   
}

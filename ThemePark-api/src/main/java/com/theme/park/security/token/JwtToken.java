package com.theme.park.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Représention d'un JWT pour l'authentification
 * 
 * @author pichat morgan
 *
 * 20 Juillet 2019
 *
 */
@Data
@AllArgsConstructor
public class JwtToken {

	private String token;

}

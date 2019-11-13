package com.theme.park.controller;

import com.theme.park.security.token.JwtService;
import com.theme.park.security.token.JwtToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api( description="API d'inscription, de connection d'utilisateur et de rafraichissement du token")
@RestController
@RequestMapping("/themeParkAPI/auth")
public class AuthenticationController {


    @Value("${jwt.prefix}")
    private String tokenPrefix;
    @Value("${jwt.header.token.auth}")
    private String headerAuth;
    @Value("${jwt.header.token.refresh}")
    private String headerRefresh;
    private JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "Rafraichissement d'un token d'authentification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès du rafraichissement"),
            @ApiResponse(code = 409, message = "Token incorrect, inexistant"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws IllegalArgumentException, AuthenticationException {

        JwtToken token = new JwtToken(jwtService.extract(request.getHeader(headerRefresh)));

        // on créer un token JWT, grace à la vérification du tokenRefresh
        String jwt = jwtService.createAuthToken(jwtService.validateRefreshToken(token));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(headerAuth, tokenPrefix + jwt);
        responseHeaders.add(headerRefresh, request.getHeader(headerRefresh));
        return ResponseEntity.ok().headers(responseHeaders).body(null);
    }

    @ApiOperation(value = "Connection d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la connction"),
            @ApiResponse(code = 409, message = "UserName / Email / PassWord incorrect"),
            @ApiResponse(code = 500, message = "Erreur interne"),
    })
    @PostMapping("/login")
    public void fakeLogin(@ApiParam("User") @RequestParam String userName, @ApiParam("Password") @RequestParam String passWord) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}

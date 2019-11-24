package com.theme.park.controller;

import com.theme.park.utilities.token.JwtService;
import com.theme.park.utilities.token.JwtToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api( description="API d'inscription, de connection d'utilisateur et de rafraichissement du token")
@RestController
@RequestMapping("/themeParkAPI/auth")
public class AuthenticationController {

    @Value("${jwt.refresh.prefix}")
    private String refreshTokenPrefix;
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
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Cookie refreshCookie = jwtService.findToken(request.getCookies(), refreshTokenPrefix);

        JwtToken refreshToken = new JwtToken(refreshCookie.getValue());

        // on créer un token JWT, grace à la vérification du tokenRefresh
        String accessToken = jwtService.createAuthToken(jwtService.validateRefreshToken(refreshToken));

        response.addCookie(jwtService.createAccessTokenCookie(accessToken));

        return ResponseEntity.ok().body(null);
    }


    @ApiOperation(value = "Connection d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la connection"),
            @ApiResponse(code = 409, message = "UserName / Email / PassWord incorrect"),
            @ApiResponse(code = 500, message = "Erreur interne"),
    })
    @PostMapping("/login")
    public void fakeLogin(@ApiParam("User") @RequestParam String userName, @ApiParam("Password") @RequestParam String passWord) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }


    @ApiOperation(value = "Deconnection d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la deconnection"),
            @ApiResponse(code = 500, message = "Erreur interne"),
    })
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.addCookie(jwtService.resetAccessTokenCookie());
        response.addCookie(jwtService.resetRefreshTokenCookie());
        return ResponseEntity.ok().body(null);
    }


}

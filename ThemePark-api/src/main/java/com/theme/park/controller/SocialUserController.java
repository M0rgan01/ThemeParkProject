package com.theme.park.controller;

import com.theme.park.business.SocialUserBusiness;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api( description="API de modification d'utilisateurs")
@RestController
@RequestMapping("/themeParkAPI")
public class SocialUserController {

    private SocialUserBusiness socialUserBusiness;

    public SocialUserController(SocialUserBusiness socialUserBusiness) {
        this.socialUserBusiness = socialUserBusiness;
    }

    @ApiOperation(value = "Ajout d'un role ADMIN pour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de l'ajout"),
            @ApiResponse(code = 404, message = "Aucune correspondance utilisateur"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PostMapping(value = "/adminRole/socialUser/{email}/{provider}")
    public ResponseEntity<?> createComment(@PathVariable String email, @PathVariable String provider) throws NotFoundException, AlreadyExistException {

        SocialUser socialUser = socialUserBusiness.setAdminRole(email, provider);

        return ResponseEntity.ok().body(socialUser);
    }
}

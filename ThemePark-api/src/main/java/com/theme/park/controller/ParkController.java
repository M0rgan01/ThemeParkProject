package com.theme.park.controller;

import com.theme.park.business.ParkBusiness;
import com.theme.park.entities.Park;
import com.theme.park.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themeParkAPI")
public class ParkController {

    private ParkBusiness parkBusiness;

    public ParkController(ParkBusiness parkBusiness) {
        this.parkBusiness = parkBusiness;
    }

    @ApiOperation(value = "Récupère un Park selon son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 409, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/userRole/parkById/{id}")
    public ResponseEntity<?> getMailByEmail(@PathVariable Long id) throws NotFoundException {

        Park park = parkBusiness.findById(id);

        return ResponseEntity.ok().body(park);
    }

}

package com.theme.park.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.business.ParkBusiness;
import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Park;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.ParkDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api( description="API de création, modification et recherche de parcs")
@RestController
@RequestMapping("/themeParkAPI")
public class ParkController {

    private ParkBusiness parkBusiness;
    private ObjectMapper objectMapper;

    public ParkController(ParkBusiness parkBusiness, ObjectMapper objectMapper) {
        this.parkBusiness = parkBusiness;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Récupère un Park selon son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/parkById/{id}")
    public ResponseEntity<?> getParkById(@PathVariable Long id) throws NotFoundException {

        Park park = parkBusiness.findById(id);

        return ResponseEntity.ok().body(park);
    }


    @ApiOperation(value = "Recherche de park")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 406, message = "Erreur de critère"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/parks/{page}/{size}")
    public ResponseEntity<?> searchParks(@PathVariable int page, @PathVariable int size, @RequestParam(required = false) String values) throws CriteriaException, IOException {

    List<SearchCriteria> searchCriteriaList = SearchCriteria.convertBase64Url(values, objectMapper);

    Page<Park> parks = parkBusiness.searchParks(searchCriteriaList, page, size);

        return ResponseEntity.ok().body(parks);
    }


    @ApiOperation(value = "Création de park")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la création"),
            @ApiResponse(code = 409, message = "Nom du parc déjà présent en persistance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PostMapping(value = "/userRole/park")
    public ResponseEntity<?> createPark(@RequestBody @Valid ParkDTO parkDTO) throws AlreadyExistException {

        Park park = parkBusiness.createPark(parkDTO);

        return ResponseEntity.ok().body(park);
    }

    @ApiOperation(value = "Modification de park")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la modification"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 409, message = "Nom du parc déjà présent en persistance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PutMapping(value = "/userRole/park/{id}")
    public ResponseEntity<?> updatePark(@PathVariable Long id ,@RequestBody @Valid ParkDTO parkDTO) throws AlreadyExistException, NotFoundException {

        Park park = parkBusiness.updatePark(id, parkDTO);

        return ResponseEntity.ok().body(park);
    }
}

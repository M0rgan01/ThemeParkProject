package com.theme.park.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.business.CountryBusiness;
import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Country;
import com.theme.park.exception.CriteriaException;
import com.theme.park.object.CountryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api( description="API de recherche de pays")
@RestController
@RequestMapping("/themeParkAPI")
public class CountryController {

    private CountryBusiness countryBusiness;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public CountryController(CountryBusiness countryBusiness, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.countryBusiness = countryBusiness;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Recherche de pays")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 406, message = "Erreur de critère"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/countries/{page}/{size}")
    public ResponseEntity<?> searchCountries(@PathVariable int page, @PathVariable int size, @RequestParam(required = false) String values) throws CriteriaException, IOException {

        List<SearchCriteria> searchCriteriaList = SearchCriteria.convertBase64Url(values, objectMapper);

        Page<Country> countries = countryBusiness.searchCountries(searchCriteriaList, page, size);
        Page<CountryDTO> countriesDTO = countries.map(country -> modelMapper.map(country, CountryDTO.class));

        return ResponseEntity.ok().body(countriesDTO);
    }



}

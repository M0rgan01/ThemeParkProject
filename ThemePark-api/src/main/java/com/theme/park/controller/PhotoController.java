package com.theme.park.controller;

import com.theme.park.business.PhotoBusiness;
import com.theme.park.entities.Photo;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CountryDTO;
import com.theme.park.object.ParkDTO;
import com.theme.park.object.PhotoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Api( description="API de gestion des photos")
@RestController
@RequestMapping("/themeParkAPI")
public class PhotoController {

    private PhotoBusiness photoBusiness;
    private ModelMapper modelMapper;

    public PhotoController(PhotoBusiness photoBusiness, ModelMapper modelMapper) {
        this.photoBusiness = photoBusiness;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Création d'une photo d'un parc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la modification"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PostMapping(value = "/userRole/photo/{id}")
    public ResponseEntity<?> setPhoto(MultipartFile file, @PathVariable(name = "id") Long id) throws IOException, NotFoundException {
        Photo photo = photoBusiness.createPhoto(file, id);
        PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);
        return ResponseEntity.ok().body(photoDTO);
    }


    @ApiOperation(value = "Récupération des photos d'un parc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/photos/parc/{id}")
    public ResponseEntity<?> getPhotosByPark(@PathVariable(name = "id") Long id) throws NotFoundException {
        List<Photo> photos = photoBusiness.getPhotosByParkId(id);
        List<PhotoDTO> photoDTOS = photos.stream().map(photo -> modelMapper.map(photo, PhotoDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(photoDTOS);
    }


    @ApiOperation(value = "Récupération d'une photo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/photo/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable(name = "id") Long id) throws IOException, NotFoundException {
        Photo photo = photoBusiness.getPhotoById(id);
        return Files.readAllBytes(Paths.get(photo.getPath()));
    }

    @ApiOperation(value = "Suppression d'une photo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 404, message = "Aucune correspondance"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @DeleteMapping(value = "/userRole/photo/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable(name = "id") Long id) throws NotFoundException {
        photoBusiness.deletePhoto(id);
        return ResponseEntity.ok().body(null);
    }
}

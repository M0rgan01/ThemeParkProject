package com.theme.park.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.business.CommentBusiness;
import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Comment;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api( description="API de création et modification de commentaires")
@RestController
@RequestMapping("/themeParkAPI")
public class CommentController {

    private CommentBusiness commentBusiness;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    public CommentController(CommentBusiness commentBusiness, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.commentBusiness = commentBusiness;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }


    @ApiOperation(value = "Recherche de commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 406, message = "Erreur de critère"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/comments/{page}/{size}")
    public ResponseEntity<?> searchComments(@PathVariable int page, @PathVariable int size, @RequestParam(required = false) String values) throws CriteriaException, IOException {

        List<SearchCriteria> searchCriteriaList = SearchCriteria.convertBase64Url(values, objectMapper);

        Page<Comment> comments = commentBusiness.searchComments(searchCriteriaList, page, size);
        Page<CommentDTO> commentDTOS = comments.map(comment -> modelMapper.map(comment, CommentDTO.class));

        return ResponseEntity.ok().body(commentDTOS);
    }



    @ApiOperation(value = "Création d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la création"),
            @ApiResponse(code = 404, message = "Aucune correspondance du parc"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PostMapping(value = "/userRole/comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO) throws NotFoundException {

        Comment comment = commentBusiness.createComment(modelMapper.map(commentDTO, Comment.class));
        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }

    @ApiOperation(value = "Modification d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la Modification"),
            @ApiResponse(code = 404, message = "Aucune correspondance du parc"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PutMapping(value = "/adminRole/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO) throws NotFoundException {

        Comment comment = commentBusiness.updateComment(id, modelMapper.map(commentDTO, Comment.class));

        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }

}

package com.theme.park.controller;

import com.theme.park.business.CommentBusiness;
import com.theme.park.entities.Comment;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api( description="API de création et modification de commentaires")
@RestController
@RequestMapping("/themeParkAPI")
public class CommentController {

    private CommentBusiness commentBusiness;

    public CommentController(CommentBusiness commentBusiness) {
        this.commentBusiness = commentBusiness;
    }

    @ApiOperation(value = "Création d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la création"),
            @ApiResponse(code = 404, message = "Aucune correspondance du parc"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PostMapping(value = "/adminRole/comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO) throws NotFoundException {

        Comment comment = commentBusiness.createComment(commentDTO);

        return ResponseEntity.ok().body(comment);
    }

    @ApiOperation(value = "Modification d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la Modification"),
            @ApiResponse(code = 404, message = "Aucune correspondance du parc"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PutMapping(value = "/adminRole/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO) throws NotFoundException {

        Comment comment = commentBusiness.updateComment(id, commentDTO);

        return ResponseEntity.ok().body(comment);
    }

}

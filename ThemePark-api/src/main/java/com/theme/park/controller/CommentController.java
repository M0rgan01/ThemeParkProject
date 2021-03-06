package com.theme.park.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theme.park.business.CommentBusiness;
import com.theme.park.entities.Comment;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CommentDTO;
import com.theme.park.object.SearchCriteria;
import com.theme.park.utilities.token.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(description = "API de création et modification de commentaires")
@RestController
@RequestMapping("/themeParkAPI")
public class CommentController {

    private CommentBusiness commentBusiness;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private JwtService jwtService;

    public CommentController(CommentBusiness commentBusiness, ModelMapper modelMapper, ObjectMapper objectMapper, JwtService jwtService) {
        this.commentBusiness = commentBusiness;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "Récupération d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 406, message = "Erreur de critère"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/comment/{id}")
    public ResponseEntity<?> getComment(@PathVariable long id) throws NotFoundException {

        Comment comment = commentBusiness.getCommentById(id);

        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }

    @ApiOperation(value = "Recherche de commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la récupération"),
            @ApiResponse(code = 406, message = "Erreur de critère"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @GetMapping(value = "/public/comments/{page}/{size}")
    public ResponseEntity<?> searchComments(@PathVariable int page,
                                            @PathVariable int size,
                                            @RequestParam(required = false) String values) throws CriteriaException, IOException {

        List<SearchCriteria> searchCriteriaList = null;
        if (values != null)
            searchCriteriaList = SearchCriteria.convertBase64Url(values, objectMapper);

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
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDTO commentDTO,
                                           HttpServletRequest request) throws NotFoundException {

        SocialUser socialUser = jwtService.getUserWithAccessCookie(request);
        Comment commentConvert = modelMapper.map(commentDTO, Comment.class);
        commentConvert.setSocialUser(socialUser);
        Comment comment = commentBusiness.createComment(commentConvert);
        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }

    @ApiOperation(value = "Modification d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la Modification"),
            @ApiResponse(code = 404, message = "Aucune correspondance du commentaire"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @PutMapping(value = "/userRole/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody @Valid CommentDTO commentDTO,
                                           HttpServletRequest request) throws NotFoundException {

        SocialUser socialUser = jwtService.getUserWithAccessCookie(request);
        Comment commentConvert = modelMapper.map(commentDTO, Comment.class);
        commentConvert.setSocialUser(socialUser);
        Comment comment = commentBusiness.updateComment(id, commentConvert);

        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }

    @ApiOperation(value = "Suppression d'un commentaire")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succès de la Suppression"),
            @ApiResponse(code = 404, message = "Aucune correspondance du commentaire"),
            @ApiResponse(code = 500, message = "Erreur interne")
    })
    @DeleteMapping(value = "/userRole/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                           HttpServletRequest request) throws NotFoundException, AlreadyExistException {

        SocialUser socialUser = jwtService.getUserWithAccessCookie(request);

        Comment comment = commentBusiness.deleteComment(id, socialUser.getSocialUserId());

        return ResponseEntity.ok().body(modelMapper.map(comment, CommentDTO.class));
    }
}

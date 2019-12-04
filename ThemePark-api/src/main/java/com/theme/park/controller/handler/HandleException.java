package com.theme.park.controller.handler;

import com.theme.park.exception.*;
import com.theme.park.utilities.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestion des erreurs des controlleurs
 *
 * @author Pichat morgan
 *
 * 05 octobre 2019
 */
@RestControllerAdvice
public class HandleException {

    private static final Logger logger = LoggerFactory.getLogger(HandleException.class);


    //////////////////////////// GLOBAL ERROR /////////////////////////////

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        ex.printStackTrace();
        logger.error("Internal error : " + ex.getMessage());
        return ErrorResponse.of("internal.error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DB out of service
    @ExceptionHandler(DataAccessResourceFailureException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorResponse handleException(DataAccessResourceFailureException ex) {
        return ErrorResponse.of("database.connection.error", HttpStatus.SERVICE_UNAVAILABLE);
    }

    //////////////////////////// JSON ERROR /////////////////////////////

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public ErrorResponse handleException(HttpMessageNotReadableException ex) {
        return ErrorResponse.of("json.error", HttpStatus.PRECONDITION_FAILED);
    }


    //////////////////////////// FILE ERROR /////////////////////////////


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleException(MaxUploadSizeExceededException ex) {
        return ErrorResponse.of("file.size.exceeded", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoSuchFileException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(NoSuchFileException ex) {
        return ErrorResponse.of("img.not.found", HttpStatus.NOT_FOUND);
    }

    //////////////////////////// VALIDATION ERROR /////////////////////////////

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleException(MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        for (FieldError fieldError : errors) {
            ErrorResponse.ErrorDetails error = new ErrorResponse.ErrorDetails();
            error.setFieldName(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errorDetails.add(error);
        }

        return ErrorResponse.of(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    //////////////////////////// AUTHENTICATION ERROR JWT REFRESH /////////////////////////////

    // compte utilisateur suspendu
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleException(DisabledException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // JWT expiré
    @ExceptionHandler(JwtExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(JwtExpiredException ex) {
        return ErrorResponse.of("jwt.refresh.expired", HttpStatus.UNAUTHORIZED);
    }

    // JWT invalid
    @ExceptionHandler(JwtInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(JwtInvalidException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // user not found
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(AuthenticationCredentialsNotFoundException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    //////////////////////////// BUSINESS ERROR /////////////////////////////

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(NotFoundException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotMatchException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(UserNotMatchException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorResponse handleException(AlreadyExistException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CriteriaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleException(CriteriaException ex) {
        return ErrorResponse.of(ex.getMessage(), HttpStatus.CONFLICT);
    }
}

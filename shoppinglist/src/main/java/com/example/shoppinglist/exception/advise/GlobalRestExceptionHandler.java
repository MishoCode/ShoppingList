package com.example.shoppinglist.exception.advise;

import com.example.shoppinglist.exception.ImageCannotBeUploadedException;
import com.example.shoppinglist.exception.ObjectAlreadyExistsException;
import com.example.shoppinglist.exception.ObjectNotFoundException;
import com.example.shoppinglist.exception.TokenAlreadyConfirmedException;
import com.example.shoppinglist.exception.TokenExpiredException;
import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_MESSAGE_TEMPLATE = "{ \"error\": \"%s\" }";

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ObjectNotFoundException e) {
        /*String bodyOfResponse = String.format(ERROR_MESSAGE_TEMPLATE, e.getMessage());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND); */
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExists(ObjectAlreadyExistsException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Object> handleConstraintViolation(PersistenceException e) {
        /*String bodyOfResponse = "{ \"error\": \"" + e.getMessage() + "\" }";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST); */
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenAlreadyConfirmedException.class)
    public ResponseEntity<Object> handleTokenAlreadyConfirmedException(TokenAlreadyConfirmedException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ImageCannotBeUploadedException.class)
    public ResponseEntity<Object> handleImageCannotBeUploadedException(ImageCannotBeUploadedException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
            .getAllErrors()
            .forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleException(Exception e, HttpStatus status) {
        String bodyOfResponse = String.format(ERROR_MESSAGE_TEMPLATE, e.getMessage());
        return new ResponseEntity<>(bodyOfResponse, status);
    }
}

package br.edu.ifpb.pweb2.pweb2.errors;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {

        final var status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ErrorResponse(status, entityNotFoundException.getMessage()), status);
    }
}

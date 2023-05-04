package br.edu.ifpb.pweb2.pweb2.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {

    public static ResponseEntity<ErrorResponse> getNotFoundResponseEntity() {
        return new ResponseEntity<>(ErrorResponseFactory.
                        createErrorResponseWithStatusAndMessage(HttpStatus.NOT_FOUND, "Recurso não encontrado"),
                HttpStatus.NOT_FOUND);
    }
}

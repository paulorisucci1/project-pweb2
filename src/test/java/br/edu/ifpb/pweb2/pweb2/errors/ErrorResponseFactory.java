package br.edu.ifpb.pweb2.pweb2.errors;

import org.springframework.http.HttpStatus;

public class ErrorResponseFactory {

    public static ErrorResponse createDefaultErrorResponse () {
        return new ErrorResponse();
    }

    public static ErrorResponse createErrorResponseWithStatusAndMessage(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus, message);
    }
}

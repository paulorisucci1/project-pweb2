package br.edu.ifpb.pweb2.pweb2.errors;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class CustomControllerAdvice {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxUploadSize;

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return makeStandardExceptionResponse(entityNotFoundException);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ModelAndView handleEntityAlreadyExistException(EntityAlreadyExistException entityAlreadyExistException) {
        return makeStandardExceptionResponse(entityAlreadyExistException);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("errorMessage", String.format("The file exceeds maximum size (%s)!", maxUploadSize));
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied() {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("errorMessage", String.format("Access denied for this resource. Please, contact an admin."));
        return modelAndView;
    }

    private ModelAndView makeStandardExceptionResponse(Exception exception) {
        final var modelAndView = new ModelAndView("exception");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }
}

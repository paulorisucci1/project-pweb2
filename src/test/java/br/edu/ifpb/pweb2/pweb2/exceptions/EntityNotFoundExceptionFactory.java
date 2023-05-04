package br.edu.ifpb.pweb2.pweb2.exceptions;

public class EntityNotFoundExceptionFactory {

    public static EntityNotFoundException getEntityNotFoundException() {
        return new EntityNotFoundException("Entity not found");
    }
}

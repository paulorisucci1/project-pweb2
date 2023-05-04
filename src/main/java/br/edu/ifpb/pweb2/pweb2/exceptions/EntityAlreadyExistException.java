package br.edu.ifpb.pweb2.pweb2.exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException() {
        super();
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}

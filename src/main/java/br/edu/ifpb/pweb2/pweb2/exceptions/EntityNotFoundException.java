package br.edu.ifpb.pweb2.pweb2.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message){
        super(message);
    }
}

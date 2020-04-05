package br.com.fooddelivery.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Integer id) {
        this(String.format("No State found with code: %s!", id));
    }
}
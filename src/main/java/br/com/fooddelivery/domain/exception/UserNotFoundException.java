package br.com.fooddelivery.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Integer id) {
        this(String.format("No User found with code: %s!", id));
    }
}
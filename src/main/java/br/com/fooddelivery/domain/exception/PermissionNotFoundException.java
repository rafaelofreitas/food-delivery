package br.com.fooddelivery.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Integer id) {
        this(String.format("No Permission found with code: %s!", id));
    }
}
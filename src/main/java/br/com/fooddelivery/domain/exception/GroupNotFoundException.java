package br.com.fooddelivery.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Integer id) {
        this(String.format("No Group found with code: %s!", id));
    }
}
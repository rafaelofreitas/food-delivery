package br.com.fooddelivery.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Integer id) {
        this(String.format("No kitchen found with code: %s!", id));
    }
}
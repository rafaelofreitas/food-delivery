package br.com.fooddelivery.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Integer id) {
        this(String.format("No Restaurant found with code: %s!", id));
    }
}
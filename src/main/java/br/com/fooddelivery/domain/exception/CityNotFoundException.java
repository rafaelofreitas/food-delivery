package br.com.fooddelivery.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Integer id) {
        this(String.format("No City found with code: %s!", id));
    }
}
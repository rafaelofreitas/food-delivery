package br.com.fooddelivery.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Integer id) {
        this(String.format("No Product found with code: %s!", id));
    }
}
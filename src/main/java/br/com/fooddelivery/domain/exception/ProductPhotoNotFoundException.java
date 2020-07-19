package br.com.fooddelivery.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Integer id) {
        this(String.format("No Product Photo found with code: %s!", id));
    }
}
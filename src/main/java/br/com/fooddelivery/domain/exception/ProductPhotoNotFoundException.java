package br.com.fooddelivery.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Integer restaurantId, Integer productId) {
        this(String.format("No Product Photo found with restaurant code: %s and product code: %s", restaurantId, productId));
    }
}
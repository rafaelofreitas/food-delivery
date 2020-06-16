package br.com.fooddelivery.domain.exception;

public class PurchaseNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PurchaseNotFoundException(String message) {
        super(message);
    }

    public PurchaseNotFoundException(Integer id) {
        this(String.format("No Purchase found with code: %s!", id));
    }
}
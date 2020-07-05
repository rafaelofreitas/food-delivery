package br.com.fooddelivery.domain.exception;

import java.util.UUID;

public class PurchaseNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PurchaseNotFoundException(String message) {
        super(message);
    }

    public PurchaseNotFoundException(UUID purchaseCode) {
        this(String.format("No Purchase found with code: %s!", purchaseCode));
    }
}
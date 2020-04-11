package br.com.fooddelivery.domain.exception;

public class PaymentNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Integer id) {
        this(String.format("No Payment found with code: %s!", id));
    }
}

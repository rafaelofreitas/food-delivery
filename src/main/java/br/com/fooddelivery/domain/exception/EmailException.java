package br.com.fooddelivery.domain.exception;

public class EmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

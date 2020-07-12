package br.com.fooddelivery.domain.exception;

public class ReportException extends RuntimeException {
    public ReportException(String msg) {
        super(msg);
    }

    public ReportException(String msg, Throwable e) {
        super(msg, e);
    }
}

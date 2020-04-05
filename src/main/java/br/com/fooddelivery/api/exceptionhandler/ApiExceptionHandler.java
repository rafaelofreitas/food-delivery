package br.com.fooddelivery.api.exceptionhandler;

import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String GENERIC_ERROR_MESSAGE_END_USER =
            "An unexpected internal system error has occurred. Try again and if the error persists, contact your system administrator.";

    private MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.SYSTEM_ERROR;

        ex.printStackTrace();

        Error error = this.createErrorBuilder(status, errorType, GENERIC_ERROR_MESSAGE_END_USER)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTITY_IN_US;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private Error.ErrorBuilder createErrorBuilder(
            HttpStatus status,
            ErrorType errorType,
            String detail
    ) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}

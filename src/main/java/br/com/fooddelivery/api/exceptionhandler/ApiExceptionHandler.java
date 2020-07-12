package br.com.fooddelivery.api.exceptionhandler;

import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String GENERIC_ERROR_MESSAGE_END_USER =
            "An unexpected internal system error has occurred. Try again and if the error persists, contact your system administrator.";

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorType = ErrorType.SYSTEM_ERROR;

        ex.printStackTrace();

        Error error = this.createErrorBuilder(status, errorType, GENERIC_ERROR_MESSAGE_END_USER)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorType = ErrorType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var errorType = ErrorType.ENTITY_IN_US;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorType = ErrorType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(
            Exception ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request,
            BindingResult bindingResult
    ) {
        var errorType = ErrorType.INVALID_DATA;
        var detail = "One or more fields are invalid. Fill in correctly and try again.";

        List<Error.Field> errorFields = bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Error.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(detail)
                .fields(errorFields)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        if (body == null) {
            body = Error.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                    .build();

            return super.handleExceptionInternal(ex, body, headers, status, request);
        }

        if (body instanceof String) {
            body = Error.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        var detail = String.format(
                "The %s resource, which you tried to access, does not exist.", exception.getRequestURL()
        );

        Error error = this.createErrorBuilder(status, ErrorType.RESOURCE_NOT_FOUND, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(exception, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return this.handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return this.handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return this.handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        var detail = "The request body is invalid. Check syntax error.";

        Error error = this.createErrorBuilder(status, ErrorType.INCOMPREHENSIBLE_MESSAGE, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String detail = String.format(
                "The URL parameter '%s' was given the value '%s', which is of an invalid type. Correct and enter a value compatible with type %s.",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
        );

        Error error = this.createErrorBuilder(status, ErrorType.INVALID_PARAMETER, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String path = this.joinPath(ex.getPath());
        var errorType = ErrorType.INCOMPREHENSIBLE_MESSAGE;
        String detail = String.format(
                "The property '%s' does not exist. Correct or remove this property and try again.", path
        );

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String path = this.joinPath(ex.getPath());
        var errorType = ErrorType.INCOMPREHENSIBLE_MESSAGE;
        var detail = String.format(
                "The property '%s' was given the value '%s', which is of an invalid type. Correct and enter a value compatible with type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        Error error = this.createErrorBuilder(status, errorType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_END_USER)
                .build();

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    private Error.ErrorBuilder createErrorBuilder(
            HttpStatus status,
            ErrorType errorType,
            String detail
    ) {
        return Error.builder()
                .timestamp(OffsetDateTime.now())
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
package com.epam.lab.controller;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss");

    private static final String ERROR = "error";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String DEFAULT_MESSAGE = "ConstraintViolationException occurred.";

    @ExceptionHandler(ServiceException.class)
    public void handleServiceError(final HttpServletResponse response, final Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleNotFoundError(final HttpServletResponse response, final Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Object> handleRepositoryException(final RepositoryException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now().format(DATE_TIME_FORMATTER));
        body.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleEntityConstraintNotValid(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String errorMessage;
        Map<String, Object> body = new LinkedHashMap<>();
        errorMessage = createErrorMessage(violations);
        body.put(TIMESTAMP, LocalDateTime.now().format(DATE_TIME_FORMATTER));
        body.put(ERROR, errorMessage);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private String createErrorMessage(final Set<ConstraintViolation<?>> violationsValue) {
        String errorMessage;
        if (!violationsValue.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violationsValue.forEach(violation -> builder.append(" ").append(violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = DEFAULT_MESSAGE;
        }
        return errorMessage;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, HttpStatus status,
                                                                  final WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now().format(DATE_TIME_FORMATTER));
        body.put(STATUS, status.value());
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        body.put(ERROR, errors);
        return new ResponseEntity<>(body, headers, status);
    }
}

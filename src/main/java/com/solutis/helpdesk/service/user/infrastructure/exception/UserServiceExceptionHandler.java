package com.solutis.helpdesk.service.user.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e) {
        var exceptionMessage = new ExceptionMessage(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionExceptionMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var exceptionMessage = new MethodArgumentNotValidExceptionExceptionMessage(e.getMessage(), e.getAllErrors());
        return ResponseEntity.badRequest().body(exceptionMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var exceptionMessage = new ExceptionMessage(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionMessage);
    }

    @ExceptionHandler(EmailAddressUnavailableException.class)
    public ResponseEntity<ExceptionMessage> handleEmailAddressUnavailableException(EmailAddressUnavailableException e) {
        var exceptionMessage = new ExceptionMessage(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionMessage);
    }
}

package com.solutis.helpdesk.service.user.infrastructure.exception;

public class EmailAddressUnavailableException extends RuntimeException {
    public EmailAddressUnavailableException(String message) {
        super(message);
    }
}

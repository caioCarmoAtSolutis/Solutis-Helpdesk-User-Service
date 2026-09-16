package com.solutis.helpdesk.service.user.infrastruture.exception;

public class EmailAddressUnavailableException extends RuntimeException {
    public EmailAddressUnavailableException(String message) {
        super(message);
    }
}

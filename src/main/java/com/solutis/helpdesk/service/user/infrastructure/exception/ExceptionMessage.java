package com.solutis.helpdesk.service.user.infrastructure.exception;

import jakarta.validation.constraints.NotBlank;

public record ExceptionMessage(
        @NotBlank
        String message
) {
}

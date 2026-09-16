package com.solutis.helpdesk.service.user.infrastruture.exception;

import jakarta.validation.constraints.NotBlank;

public record ExceptionMessage(
        @NotBlank
        String message
) {
}

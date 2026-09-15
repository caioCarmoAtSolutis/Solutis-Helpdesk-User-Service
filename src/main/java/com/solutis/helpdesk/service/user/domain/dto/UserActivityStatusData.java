package com.solutis.helpdesk.service.user.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserActivityStatusData(
        @NotNull
        UUID id,

        @NotNull
        Boolean active
) {
}

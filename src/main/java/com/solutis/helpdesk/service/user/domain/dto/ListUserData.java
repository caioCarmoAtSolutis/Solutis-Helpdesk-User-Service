package com.solutis.helpdesk.service.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ListUserData(
        @NotNull
        UUID id,

        @NotBlank
        @Max(150)
        String name,

        @NotBlank
        @Email
        @Max(100)
        String email,

        @NotNull
        UserRoleData role,

        @NotNull
        Boolean active
) {
}

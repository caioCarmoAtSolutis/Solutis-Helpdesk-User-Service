package com.solutis.helpdesk.service.user.domain.dto;

import jakarta.validation.constraints.*;

public record UserData(
        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotNull
        UserRoleData role
) {
}

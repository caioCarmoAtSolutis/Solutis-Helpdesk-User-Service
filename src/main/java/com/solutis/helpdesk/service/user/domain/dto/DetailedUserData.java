package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record DetailedUserData(
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
        Boolean active,

        @NotNull
        LocalDateTime createdAt
) {
        public DetailedUserData(User user) {
                this(user.getId(), user.getName(), user.getEmail(), new UserRoleData(user.getRole()), user.getActive(), user.getCreatedAt());
        }
}

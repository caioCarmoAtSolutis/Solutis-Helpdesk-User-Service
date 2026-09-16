package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.User;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record DetailedUserData(
        @NotNull
        UUID id,

        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank
        @Email
        @Size(max = 100)
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

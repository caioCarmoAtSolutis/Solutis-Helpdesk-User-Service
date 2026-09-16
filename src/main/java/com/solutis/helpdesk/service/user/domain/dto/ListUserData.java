package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ListUserData(
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
        Boolean active
) {
        public ListUserData(User user) {
                this(user.getId(), user.getName(), user.getEmail(), new UserRoleData(user.getRole()), user.getActive());
        }
}

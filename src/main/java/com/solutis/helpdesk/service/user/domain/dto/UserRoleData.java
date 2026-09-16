package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.Role;
import com.solutis.helpdesk.service.user.domain.model.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserRoleData(
        @NotNull
        Role role
) {
    public UserRoleData(UserRole role) {
        this(role.getRole());
    }
}

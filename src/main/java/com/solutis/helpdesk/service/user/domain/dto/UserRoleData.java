package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.Role;
import com.solutis.helpdesk.service.user.domain.model.UserRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record UserRoleData(
        @NotNull
        @Max(50)
        Role value
) {
    public UserRoleData(UserRole role) {
        this(role.getValue());
    }
}

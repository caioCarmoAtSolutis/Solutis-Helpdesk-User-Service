package com.solutis.helpdesk.service.user.domain.dto;

import com.solutis.helpdesk.service.user.domain.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserActivityStatusData(
        @NotNull
        UUID id,

        @NotNull
        Boolean active
) {
        public UserActivityStatusData(User user) {
                this(user.getId(), user.getActive());
        }
}

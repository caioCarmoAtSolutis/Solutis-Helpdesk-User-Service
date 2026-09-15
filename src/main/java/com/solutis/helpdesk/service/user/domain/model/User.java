package com.solutis.helpdesk.service.user.domain.model;

import com.solutis.helpdesk.service.user.domain.dto.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    private UserRole role;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public User(UserData data, UserRole role) {
        this.id = UUID.randomUUID();
        this.name = data.name();
        this.email = data.email();
        this.role = role;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public void update(UserData data, UserRole role) {
        this.name = data.name();
        this.email = data.email();
        this.role = role;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}

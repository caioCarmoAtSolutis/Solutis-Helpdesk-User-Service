package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.domain.dto.CreateUserData;
import com.solutis.helpdesk.service.user.domain.dto.ListUserData;
import com.solutis.helpdesk.service.user.domain.dto.UserActivityStatusData;
import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserData> createUser(@Valid @RequestBody CreateUserData data, UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<ListUserData> getUsers(@PageableDefault(size = 10) Pageable page) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("{id}")
    public ResponseEntity<ListUserData> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("{id}/deactivate")
    public ResponseEntity<UserActivityStatusData> deactivateUser(@PathVariable UUID id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("{id}/activate")
    public ResponseEntity<UserActivityStatusData> activateUser(@PathVariable UUID id) {
        return ResponseEntity.ok(null);
    }
}

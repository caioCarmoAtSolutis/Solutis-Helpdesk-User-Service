package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.domain.dto.ListUserData;
import com.solutis.helpdesk.service.user.domain.dto.UserActivityStatusData;
import com.solutis.helpdesk.service.user.domain.dto.DetailedUserData;
import com.solutis.helpdesk.service.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<DetailedUserData> createUser(@Valid @RequestBody UserData data, UriComponentsBuilder uriComponentsBuilder) {
        DetailedUserData createdUser = userService.createUser(data);
        URI location = uriComponentsBuilder.path("/users/" + "{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserData>> getUsers(@PageableDefault(size = 10) Pageable pageable) {
        Page<ListUserData> page = userService.getUsers(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<ListUserData> getUser(@PathVariable UUID id) {
        ListUserData userData = userService.getUser(id);
        return ResponseEntity.ok(userData);
    }

    @PutMapping("{id}")
    public ResponseEntity<DetailedUserData> updateUser(@PathVariable UUID id, @Valid @RequestBody UserData data) {
        DetailedUserData updatedUser = userService.updateUser(id, data);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("{id}/deactivate")
    public ResponseEntity<UserActivityStatusData> deactivateUser(@PathVariable UUID id) {
        UserActivityStatusData deactivatedUser = userService.deactivateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }

    @PostMapping("{id}/activate")
    public ResponseEntity<UserActivityStatusData> activateUser(@PathVariable UUID id) {
        UserActivityStatusData deactivatedUser = userService.activateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }
}

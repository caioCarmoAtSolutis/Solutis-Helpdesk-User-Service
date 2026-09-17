package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.domain.dto.ListUserData;
import com.solutis.helpdesk.service.user.domain.dto.UserActivityStatusData;
import com.solutis.helpdesk.service.user.domain.dto.DetailedUserData;
import com.solutis.helpdesk.service.user.infrastruture.exception.ExceptionMessage;
import com.solutis.helpdesk.service.user.infrastruture.exception.MethodArgumentNotValidExceptionExceptionMessage;
import com.solutis.helpdesk.service.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Create new user")
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MethodArgumentNotValidExceptionExceptionMessage.class)) }) })
    @PostMapping
    public ResponseEntity<DetailedUserData> createUser(@Valid @RequestBody UserData data, UriComponentsBuilder uriComponentsBuilder) {
        DetailedUserData createdUser = userService.createUser(data);
        URI location = uriComponentsBuilder.path("/users/" + "{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @Tag(name = "List user")
    @Operation(summary = "List all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)) }) })
    @GetMapping
    public ResponseEntity<Page<ListUserData>> getUsers(@PageableDefault(size = 10) Pageable pageable) {
        Page<ListUserData> page = userService.getUsers(pageable);
        return ResponseEntity.ok(page);
    }

    @Tag(name = "List user")
    @Operation(summary = "List user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)) }) })
    @GetMapping("{id}")
    public ResponseEntity<ListUserData> getUser(@PathVariable UUID id) {
        ListUserData userData = userService.getUser(id);
        return ResponseEntity.ok(userData);
    }

    @Tag(name = "Update user")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MethodArgumentNotValidExceptionExceptionMessage.class)) }) })
    @PutMapping("{id}")
    public ResponseEntity<DetailedUserData> updateUser(@PathVariable UUID id, @Valid @RequestBody UserData data) {
        DetailedUserData updatedUser = userService.updateUser(id, data);
        return ResponseEntity.ok(updatedUser);
    }

    @Tag(name = "Update user")
    @Operation(summary = "Deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)) }) })
    @PostMapping("{id}/deactivate")
    public ResponseEntity<UserActivityStatusData> deactivateUser(@PathVariable UUID id) {
        UserActivityStatusData deactivatedUser = userService.deactivateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }

    @Tag(name = "Update user")
    @Operation(summary = "Activate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserData.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)) }) })
    @PostMapping("{id}/activate")
    public ResponseEntity<UserActivityStatusData> activateUser(@PathVariable UUID id) {
        UserActivityStatusData deactivatedUser = userService.activateUser(id);
        return ResponseEntity.ok(deactivatedUser);
    }
}

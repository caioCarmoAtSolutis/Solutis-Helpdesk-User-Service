package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.domain.dto.UserRoleData;
import com.solutis.helpdesk.service.user.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional // Automatically rolls back database changes made by MockMvc after each test method
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<UserData> userDataJson;

    private UUID createTestUser(String name, String email, Role role) throws Exception {
        UserData user = new UserData(name, email, new UserRoleData(role));
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(user).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        String location = response.getHeader("Location");
        assertThat(location).isNotNull();
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    @Test
    @DisplayName("Create user with valid data: ResponseEntity.status = 201, Created")
    void createUserWithValidData() throws Exception {
        String name = "teste_1";
        String email = "teste_1@gmail.com";
        Role role = Role.ADMIN;
        UserData user = new UserData(name, email, new UserRoleData(role));
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(user).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).contains(name);
        assertThat(response.getContentAsString()).contains(email);
        assertThat(response.getContentAsString()).contains(Role.ADMIN.toString());
        assertThat(response.getHeader("Location")).contains("/users/");
    }

    @Test
    @DisplayName("Create user with blank name: ResponseEntity.status = 400, Bad Request")
    void createUserWithBlankNameReturnsBadRequest() throws Exception {
        UserData user = new UserData("", "valid.email@gmail.com", new UserRoleData(Role.CLIENT));
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(user).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Create user with invalid email format: ResponseEntity.status = 400, Bad Request")
    void createUserWithInvalidEmailReturnsBadRequest() throws Exception {
        UserData user = new UserData("Valid Name", "invalid-email-format", new UserRoleData(Role.CLIENT));
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(user).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Create user with duplicate email: ResponseEntity.status = 400, Bad Request")
    void createUserWithDuplicateEmailReturnsBadRequest() throws Exception {
        String email = "duplicate.user@gmail.com";
        createTestUser("First User", email, Role.CLIENT);

        UserData duplicateUser = new UserData("Second User", email, new UserRoleData(Role.ADMIN));
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(duplicateUser).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("is being used");
    }

    @Test
    @DisplayName("Get users: ResponseEntity.status = 200, OK")
    void getUsersReturnsOkAndPaginatedList() throws Exception {
        String name = "list_user_test";
        String email = "list_user@gmail.com";
        createTestUser(name, email, Role.CLIENT);

        MockHttpServletResponse response = mockMvc.perform(
                get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(name);
        assertThat(response.getContentAsString()).contains(email);
        assertThat(response.getContentAsString()).contains("content");
        assertThat(response.getContentAsString()).contains("pageable");
    }

    @Test
    @DisplayName("Get users with custom pageable: ResponseEntity.status = 200, OK")
    void getUsersWithCustomPageableReturnsOk() throws Exception {
        createTestUser("Pageable User", "pageable_user@gmail.com", Role.TECHNICIAN);

        MockHttpServletResponse response = mockMvc.perform(
                get("/users?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"size\":5");
    }

    @Test
    @DisplayName("Get user by ID when user exists: ResponseEntity.status = 200, OK")
    void getUserById_whenUserExistsReturnsOk() throws Exception {
        String name = "get_user_by_id";
        String email = "get_user_by_id@gmail.com";
        UUID userId = createTestUser(name, email, Role.TECHNICIAN);

        MockHttpServletResponse response = mockMvc.perform(
                get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(userId.toString());
        assertThat(response.getContentAsString()).contains(name);
        assertThat(response.getContentAsString()).contains(email);
        assertThat(response.getContentAsString()).contains(Role.TECHNICIAN.toString());
    }

    @Test
    @DisplayName("Get user by ID when user does not exist: ResponseEntity.status = 400, Bad Request")
    void getUserById_whenUserDoesNotExistReturnsBadRequest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                get("/users/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("not found");
    }

    @Test
    @DisplayName("Update user with valid data: ResponseEntity.status = 200, OK")
    void updateUserWithValidDataReturnsOk() throws Exception {
        UUID userId = createTestUser("Original Name", "original@gmail.com", Role.CLIENT);

        String updatedName = "Updated Name";
        String updatedEmail = "updated_unique@gmail.com";
        Role updatedRole = Role.TECHNICIAN;
        UserData updatedData = new UserData(updatedName, updatedEmail, new UserRoleData(updatedRole));

        MockHttpServletResponse response = mockMvc.perform(
                put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(updatedData).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(userId.toString());
        assertThat(response.getContentAsString()).contains(updatedName);
        assertThat(response.getContentAsString()).contains(updatedEmail);
        assertThat(response.getContentAsString()).contains(Role.TECHNICIAN.toString());
    }

    @Test
    @DisplayName("Update user when user does not exist: ResponseEntity.status = 400, Bad Request")
    void updateUserWhenUserDoesNotExistReturnsBadRequest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        UserData data = new UserData("Non Existent", "nonexistent@gmail.com", new UserRoleData(Role.CLIENT));

        MockHttpServletResponse response = mockMvc.perform(
                put("/users/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(data).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("not found");
    }

    @Test
    @DisplayName("Update user with invalid data (blank name): ResponseEntity.status = 400, Bad Request")
    void updateUserWithInvalidDataReturnsBadRequest() throws Exception {
        UUID userId = createTestUser("Valid Name", "valid_for_update@gmail.com", Role.CLIENT);
        UserData invalidData = new UserData("", "new_email@gmail.com", new UserRoleData(Role.CLIENT));

        MockHttpServletResponse response = mockMvc.perform(
                put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson.write(invalidData).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deactivate user when user exists: ResponseEntity.status = 200, OK")
    void deactivateUserWhenUserExistsReturnsOk() throws Exception {
        UUID userId = createTestUser("Active User", "active_user@gmail.com", Role.CLIENT);

        MockHttpServletResponse response = mockMvc.perform(
                post("/users/{id}/deactivate", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(userId.toString());
        assertThat(response.getContentAsString()).contains("\"active\":false");
    }

    @Test
    @DisplayName("Deactivate user when user does not exist: ResponseEntity.status = 400, Bad Request")
    void deactivateUserWhenUserDoesNotExistReturnsBadRequest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                post("/users/{id}/deactivate", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("not found");
    }

    @Test
    @DisplayName("Activate user when user exists: ResponseEntity.status = 200, OK")
    void activateUserWhenUserExistsReturnsOk() throws Exception {
        UUID userId = createTestUser("To Deactivate", "to_deactivate@gmail.com", Role.CLIENT);

        // Deactivate first
        mockMvc.perform(post("/users/{id}/deactivate", userId)).andReturn();

        // Now activate
        MockHttpServletResponse response = mockMvc.perform(
                post("/users/{id}/activate", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(userId.toString());
        assertThat(response.getContentAsString()).contains("\"active\":true");
    }

    @Test
    @DisplayName("Activate user when user does not exist: ResponseEntity.status = 400, Bad Request")
    void activateUserWhenUserDoesNotExistReturnsBadRequest() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                post("/users/{id}/activate", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("not found");
    }
}
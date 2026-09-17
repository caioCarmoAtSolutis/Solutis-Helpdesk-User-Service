package com.solutis.helpdesk.service.user.controller;

import com.solutis.helpdesk.service.user.domain.dto.DetailedUserData;
import com.solutis.helpdesk.service.user.domain.dto.UserData;
import com.solutis.helpdesk.service.user.domain.dto.UserRoleData;
import com.solutis.helpdesk.service.user.domain.model.Role;
import org.springframework.transaction.annotation.Transactional;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @Autowired
    private JacksonTester<UserRoleData> userRoleDataJson;

    @Autowired
    private JacksonTester<DetailedUserData> detailedUserDataJson;

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
    }

}
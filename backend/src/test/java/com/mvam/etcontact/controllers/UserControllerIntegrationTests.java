package com.mvam.etcontact.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvam.etcontact.dto.UserInsertDTO;
import com.mvam.etcontact.utils.Factory;
import com.mvam.etcontact.utils.SpringBootContextTest;
import com.mvam.etcontact.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTests extends SpringBootContextTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminUsername;
    private String adminPassword;
    private String visitorUsername;
    private String visitorPassword;

    private UserInsertDTO userInsertDTO;

    @BeforeEach
    void setUp() throws Exception {

        adminUsername = "admin@gmail.com";
        adminPassword = "admin";
        visitorUsername = "marcos@gmail.com";
        visitorPassword = "marcos";

        userInsertDTO = Factory.createUserInsertDTO(1L);
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsers() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").isNotEmpty());
        result.andExpect(jsonPath("$.content[0].name").isNotEmpty());
        result.andExpect(jsonPath("$.content[0].email").isNotEmpty());
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsersWithSpecifSizeTotalElementsAndTotalPages() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get("/users?page=0&linesPerPage=1")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.number").value(0));
        result.andExpect(jsonPath("$.size").value(1));
        result.andExpect(jsonPath("$.totalElements").value(2));
        result.andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsersThatContainsInformedName() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get("/users?name=Marcos")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").value(2L));
        result.andExpect(jsonPath("$.content[0].name").value("Marcos Vinicius"));
        result.andExpect(jsonPath("$.content[0].email").value("marcos@gmail.com"));
        result.andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createShouldCreateNewUserWhenLoggedAsAdminAndDataIsValid() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        String jsonBody = objectMapper.writeValueAsString(userInsertDTO);

        ResultActions result =
                mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        result.andExpect(jsonPath("$.name").value(userInsertDTO.getName()));
        result.andExpect(jsonPath("$.cpf").value(userInsertDTO.getCpf()));
        result.andExpect(jsonPath("$.birthDate").value(userInsertDTO.getBirthDate().toString()));
        result.andExpect(jsonPath("$.email").value(userInsertDTO.getEmail()));
    }

    @Test
    void createShouldReturnUnprocessableEntityWhenRequiredFieldNotFilled() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        userInsertDTO.setName(null);

        String jsonBody = objectMapper.writeValueAsString(userInsertDTO);

        ResultActions result =
                mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
    }

    @Test
    void createShouldReturnUnprocessableEntityWhenEmailAlreadyExists() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        userInsertDTO.setEmail(adminUsername);

        String jsonBody = objectMapper.writeValueAsString(userInsertDTO);

        ResultActions result =
                mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
        result.andExpect(jsonPath("$.errors[0].message").value("Email already exists"));
    }

    @Test
    void createShouldReturnAccessDeniedWhenLoggedAsVisitor() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, visitorUsername, visitorPassword);

        String jsonBody = objectMapper.writeValueAsString(userInsertDTO);

        ResultActions result =
                mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isForbidden());
    }
}